package com.eno.tkg.classSchedule;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
class ClassScheduleService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	private final int MAX_ASSIGN_STUDENTS_TO_LECTURER = 2;

	/**
	 * // 本日の授業に関する情報を返す
	 * 
	 * @param date 情報を取得したい日付
	 * @return json 授業情報
	 * @throws JsonProcessingException
	 *
	 */
	String getTargetDateClassSchedule(final Date date) throws JsonProcessingException {
		// 本日の授業に関する情報取得
		List<StudentScheduleNormal> classes = studentScheduleNormalRepository.findAllByClassDateOrderByPeriod(date);

		// 整形
		List<Map<String, Object>> returnJsonLiteral = prepareClassInfo(classes);
		// 文字列変換
		String strJson = getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	/**
	 * // チェックをいれた授業の生徒の授業予定を取得
	 * 
	 * @param content 予定取得に必要な情報
	 * @return json 授業予定
	 * @throws JsonProcessingException
	 *
	 */
	String getSelectStudentClassSchedule(final String studentId) throws JsonProcessingException {
		Integer idToInt = Integer.parseInt(studentId);
		List<StudentScheduleNormal> studentSchedule = studentScheduleNormalRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(idToInt), new Date());
		List<Map<String, Object>> returnJsonLiteral = prepareStudentScheduleInfo(studentSchedule);
		String strJson = getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	/**
	 * // 振替実行
	 * 
	 * @param content 更新に必要な情報
	 * @return 更新情報
	 *
	 */
	StudentScheduleNormal updateTargetClassSchedule(final String content) throws Exception {

		// 4,2022-06-09,8
		// csvの情報を分離
		String studentScheduleId = content.split(",")[0];
		String alterClassDate = content.split(",")[1];
		String alterPeriod = content.split(",")[2];

		// DBから現在の情報を取得
		Optional<StudentScheduleNormal> updateTarget = studentScheduleNormalRepository
				.findById(Integer.parseInt(studentScheduleId));
		Date date = validateBeforeUpdateClass(alterClassDate, alterPeriod, updateTarget);

		// 日付情報、コマ情報、振替フラグ,更新時刻、これらの項目を更新（TODO:場合によっては新しくコンストラクタ作成）
		updateTarget.get().setClassDate(date);
		updateTarget.get().setPeriod(alterPeriod);
		updateTarget.get().setRescheduleFlg(true);
		updateTarget.get().setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		StudentScheduleNormal updateContent = updateTarget.get();
		return studentScheduleNormalRepository.saveAndFlush(updateContent);
	}

	// 文字列型の日付をDate型に変更
	Date convertStrDateToDateType(final String dateStr) {
		String strDate = dateStr.replace("-", "/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 授業一覧画面表示用に整形
	private List<Map<String, Object>> prepareClassInfo(List<StudentScheduleNormal> classes) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal eachClass : classes) {
			Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
			// TODO:振替授業である場合に、その情報を画面に表示しておいた方がいいかと・・
			// 理想は、いつからの振替かだが、振替期限から分かるか
			eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
			eachRowInfoMap.put("period", eachClass.getPeriod());
			eachRowInfoMap.put("grade", eachClass.getStudent().getGrade().getDisplayName());
			eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
			eachRowInfoMap.put("studentId", String.valueOf(eachClass.getStudent().getId()));
			eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
			eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());

			String rescheduleDateStart = dateToDateStr(eachClass.getRescheduleDateStart());
			String rescheduleDateEnd = dateToDateStr(eachClass.getRescheduleDateLast());
			eachRowInfoMap.put("rescheduleDateStart", rescheduleDateStart.replace("-", "/"));
			eachRowInfoMap.put("rescheduleDateEnd", rescheduleDateEnd.replace("-", "/"));

			returnJsonLiteral.add(eachRowInfoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}
	
	// 生徒予定表示用に整形
		private List<Map<String, Object>> prepareStudentScheduleInfo(List<StudentScheduleNormal> studentSchedule) {
			List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
			for (StudentScheduleNormal eachClass : studentSchedule) {
				Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
				// TODO:振替授業である場合に、その情報を画面に表示しておいた方がいいかと・・
				// 理想は、いつからの振替かだが、振替期限から分かるか
				eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
				eachRowInfoMap.put("period", eachClass.getPeriod());
				eachRowInfoMap.put("grade", eachClass.getStudent().getGrade().getDisplayName());
				eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
				eachRowInfoMap.put("studentId", String.valueOf(eachClass.getStudent().getId()));
				eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
				eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());
				
				String classDate = dateToDateStr(eachClass.getClassDate());
				eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
//				String rescheduleDateStart = dateToDateStr(eachClass.getRescheduleDateStart());
//				String rescheduleDateEnd = dateToDateStr(eachClass.getRescheduleDateLast());
//				eachRowInfoMap.put("rescheduleDateStart", rescheduleDateStart.replace("-", "/"));
//				eachRowInfoMap.put("rescheduleDateEnd", rescheduleDateEnd.replace("-", "/"));
				returnJsonLiteral.add(eachRowInfoMap);
			}
			return Collections.unmodifiableList(returnJsonLiteral);
		}

	private String dateToDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	// 授業更新時のバリデーションチェック
	private Date validateBeforeUpdateClass(String alterClassDate, String alterPeriod,
			Optional<StudentScheduleNormal> updateTarget) throws Exception {
		// IDがない→まずありえないケースではある
		if (updateTarget.isEmpty()) {
			throw new Exception("IDが存在しません");
		}

		Date date = convertStrDateToDateType(alterClassDate);
		// 振替期限の間に設定できていない場合
		if (date.before(updateTarget.get().getRescheduleDateStart())
				|| date.after(updateTarget.get().getRescheduleDateLast())) {
			throw new Exception("振替後日程が振替期間外になっています");
		}
		// 振替になっていないケース
		if (updateTarget.get().getClassDate().equals(date) && updateTarget.get().getPeriod().equals(alterPeriod)) {
			throw new Exception("振替後日程が振替前日程と同じです");
		}

		// 振替先の日付・コマで担当する講師が既に1対2になっているケース
		List<StudentScheduleNormal> lecturerAlterClassScheduleList = studentScheduleNormalRepository
				.findAllByLecturerAndClassDateAndPeriod(new Lecturer(updateTarget.get().getLecturer().getId()), date,
						alterPeriod);
		if (lecturerAlterClassScheduleList.size() >= MAX_ASSIGN_STUDENTS_TO_LECTURER) {
			throw new Exception("振替後日程にて、担当講師が既に2人の生徒を担当予定です");
		}

		// 振替先日程に、既に生徒の授業が組まれている場合
		Optional<StudentScheduleNormal> studentAlterClassSchedule = studentScheduleNormalRepository
				.findByStudentAndClassDateAndPeriod(new Student(updateTarget.get().getStudent().getId()), date,
						alterPeriod);
		if (studentAlterClassSchedule.isPresent()) {
			throw new Exception("振替後日程にて、その生徒には既に別の授業が予定されています");
		}
		return date;
	}

	// 取得データをJson形式にする
	private String getDataToJsonFormat(Object returnJsonLiteral) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String strJson = "";
		strJson = mapper.writeValueAsString(returnJsonLiteral);
		return strJson;
	}
}
