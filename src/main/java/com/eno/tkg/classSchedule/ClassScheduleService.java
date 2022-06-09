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
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
class ClassScheduleService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	/**
	 * // 本日の授業に関する情報を返す
	 * 
	 * @param date 情報を取得したい日付
	 * @return json 授業情報
	 *
	 */
	String getTargetDateClassSchedule(final Date date) {
		// 本日の授業に関する情報取得
		List<StudentScheduleNormal> classes = studentScheduleNormalRepository.findAllByClassDateOrderByPeriod(date);

		// 整形
		List<Map<String, Object>> returnJsonLiteral = prepareClassInfo(classes);
		// 文字列変換
		ObjectMapper mapper = new ObjectMapper();
		String strJson = "";
		try {
			strJson = mapper.writeValueAsString(returnJsonLiteral);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

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
		//csvの情報を分離
		String studentScheduleId = content.split(",")[0];
		String alterClassDate = content.split(",")[1];
		String alterPeriod = content.split(",")[2];

		//DBから現在の情報を取得
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
			eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
			eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String rescheduleDateStart = dateFormat.format(eachClass.getRescheduleDateStart());
			String rescheduleDateEnd = dateFormat.format(eachClass.getRescheduleDateLast());
			eachRowInfoMap.put("rescheduleDateStart", rescheduleDateStart.replace("-", "/"));
			eachRowInfoMap.put("rescheduleDateEnd", rescheduleDateEnd.replace("-", "/"));

			returnJsonLiteral.add(eachRowInfoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

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
		return date;
	}

}
