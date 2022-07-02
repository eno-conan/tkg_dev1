package com.eno.tkg.student.specialSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.StudentAttendanceSpecialRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.TimeTableSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetSpecialScheduleService {

	@Autowired
	private GetSpecialScheduleServiceSupport getSpecialScheduleServiceSupport;

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private TimeTableSpecialRepository timeTableSpecialRepository;

	@Autowired
	private StudentAttendanceSpecialRepository studentAttendanceSpecialRepository;

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param studentId 生徒ID
	 * @return json コマごとに整形した授業予定一覧
	 * @throws JsonProcessingException
	 *
	 */
	// 戻り値はMapを使用
	String getTargetStudentSpecialSchedule(final String studentId, final String specialSeasonId)
			throws JsonProcessingException {

		// フロントに返す全授業情報（コマごとに情報を格納）
		Map<String, List<Map<String, Object>>> classesGropingPeriodMap = new LinkedHashMap<>();

		// 生徒の出欠予定取得（これから実装して画面に組み込む）
		// 現在の登録情報状況を取得
		// 生徒の
		List<StudentAttendanceSpecial> currentAttendanceInfo = studentAttendanceSpecialRepository
				.findBySpecialSeasonAndStudent(new SpecialSeason(Integer.parseInt(specialSeasonId)),
						new Student(Integer.parseInt(studentId)));

		currentAttendanceInfo.forEach(inof -> {
			System.out.println(inof.getTimeTableSpecial().getId());
		});

		// 生徒スケジュール取得
		List<Optional<StudentScheduleSpecial>> studentSchedule = studentScheduleSpecialRepository
				.findByStudentOrderByClassDate(new Student(Integer.parseInt(studentId)));

		// コマごと、日付ごとに現在の授業情報を取得
		for (int period = 2; period < 5; period++) {
			String currentPeriod = String.valueOf(period);
			List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = setInfroEachPeriod(currentPeriod,
					studentSchedule, specialSeasonId, currentAttendanceInfo);
			// 全日程の情報を格納するためのList
			List<Map<String, Object>> allDateInfoMap = new ArrayList<>();
			// 日付ごとに処理
			for (Optional<StudentScheduleSpecial> eachClass : divideExistClassOrNotList) {
				Map<String, Object> eachDateInfoMap = setReturnInfoEachDate(eachClass);
				allDateInfoMap.add(eachDateInfoMap);
			}
			// コマ数をキー、そのコマに紐づく全日程の情報をMapに追加
			classesGropingPeriodMap.put(currentPeriod, allDateInfoMap);
		}
		String strJson = UseOverFunction.getDataToJsonFormat(classesGropingPeriodMap);
		return strJson;
	}

	// ==========================================================================================

	// コマごとに情報設定
	private List<Optional<StudentScheduleSpecial>> setInfroEachPeriod(final String period,
			List<Optional<StudentScheduleSpecial>> studentSchedule, final String specialSeasonId,
			List<StudentAttendanceSpecial> currentAttendanceInfo) {

		// 2コマから8コマまで
		String currentPeriod = String.valueOf(period);

		// コマごとに情報のフィルタリング（timeTableIdから何コマか取得し、現在のloopのコマ数と一致するか判定）
		// 2コマなら、IDが1,8,15,22という値を取得する
		List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod = studentSchedule.stream()
				.filter(s -> s.get().getTimeTableSpecial().getPeriod().equals(currentPeriod))
				.collect(Collectors.toList());
		// timeTable情報をコマごとに取得
		List<Optional<TimeTableSpecial>> timetableSpecialInfo = timeTableSpecialRepository.findByPeriod(currentPeriod);

		List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = getSpecialScheduleServiceSupport
				.setInfoByClassExist(specialSeasonId, filteringClassesByPeriod, timetableSpecialInfo,
						currentAttendanceInfo);

		return Collections.unmodifiableList(divideExistClassOrNotList);
	}

	// 日付ごとに情報設定
	private Map<String, Object> setReturnInfoEachDate(Optional<StudentScheduleSpecial> eachClass) {
		Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
		// timeTableIdと日付情報だけは画面表示およびPUT通信時に必要なため、現在スケジュールが入っていない場合も登録
		eachRowInfoMap.put("id", eachClass.get().getId() == null ? "" : String.valueOf(eachClass.get().getId()));
		eachRowInfoMap.put("studentId",
				eachClass.get().getId() == null ? "" : String.valueOf(eachClass.get().getStudent().getId()));
		eachRowInfoMap.put("subject",
				eachClass.get().getId() == null ? "" : eachClass.get().getSubject().getDisplayName());
		eachRowInfoMap.put("lecturerName",
				eachClass.get().getId() == null ? "" : eachClass.get().getLecturer().getLecturerName());
		eachRowInfoMap.put("timeTableSpecialId", eachClass.get().getTimeTableSpecial().getId() == -1 ? "stu-abs"
				: String.valueOf(eachClass.get().getTimeTableSpecial().getId()));
		String classDate = UseOverFunction.dateToDateStr(eachClass.get().getClassDate());
		eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
		return Collections.unmodifiableMap(eachRowInfoMap);
	}

}
