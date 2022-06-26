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

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.TimeTableSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetStudentSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	@Autowired
	private TimeTableSpecialRepository timeTableSpecialRepository;

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

		// 生徒スケジュール取得
		List<Optional<StudentScheduleSpecial>> studentSchedule = studentScheduleSpecialRepository
				.findByStudentOrderByClassDate(new Student(Integer.parseInt(studentId)));

		// コマごと、日付ごとに現在の授業情報を取得
		for (int period = 2; period < 4; period++) {
			String currentPeriod = String.valueOf(period);
			List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = setInfroEachPeriod(currentPeriod,
					studentSchedule, specialSeasonId);
			// 全日程の情報を格納するためのList
			List<Map<String, Object>> allDateInfoMap = new ArrayList<>();
			// 日付ごとに処理
			for (Optional<StudentScheduleSpecial> eachClass : divideExistClassOrNotList) {
				Map<String, Object> eachDateInfoMap = setReturnInfoEachDate(eachClass, divideExistClassOrNotList);
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
			List<Optional<StudentScheduleSpecial>> studentSchedule, final String specialSeasonId) {

		// 2コマから8コマまで
		String currentPeriod = String.valueOf(period);

		// コマごとに情報のフィルタリング（timeTableIdから何コマか取得し、現在のloopの数と一致するか判定）
		List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod = studentSchedule.stream()
				.filter(s -> s.get().getTimeTableSpecial().getPeriod().equals(currentPeriod))
				.collect(Collectors.toList());
		// timeTable情報をコマごとに取得
		List<Optional<TimeTableSpecial>> timetableSpecialInfo = timeTableSpecialRepository.findByPeriod(currentPeriod);

		List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = setInfoByClassExist(specialSeasonId,
				filteringClassesByPeriod, timetableSpecialInfo);

		return Collections.unmodifiableList(divideExistClassOrNotList);
	}

	// 日付ごとに情報設定
	private Map<String, Object> setReturnInfoEachDate(Optional<StudentScheduleSpecial> eachClass,
			List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList) {
		Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
		// timeTableIdと日付情報だけは画面表示およびPUT通信時に必要なため、現在スケジュールが入っていない場合も登録
		eachRowInfoMap.put("id", eachClass.get().getId() == null ? "" : String.valueOf(eachClass.get().getId()));
		eachRowInfoMap.put("studentId",
				eachClass.get().getId() == null ? "" : String.valueOf(eachClass.get().getStudent().getId()));
		eachRowInfoMap.put("subject",
				eachClass.get().getId() == null ? "" : eachClass.get().getSubject().getDisplayName());
		eachRowInfoMap.put("lecturerName",
				eachClass.get().getId() == null ? "" : eachClass.get().getLecturer().getLecturerName());
//
		eachRowInfoMap.put("timeTableSpecialId", String.valueOf(eachClass.get().getTimeTableSpecial().getId()));
		String classDate = UseOverFunction.dateToDateStr(eachClass.get().getClassDate());
		eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
		return Collections.unmodifiableMap(eachRowInfoMap);
	}

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param filteringClassesByPeriod コマごとの授業情報
	 * @param timetableSpecialInfo     timeTableIdに紐づく情報
	 * @return divideExistClassOrNotList 授業あり/なしの情報
	 *
	 */
	private List<Optional<StudentScheduleSpecial>> setInfoByClassExist(final String specialSeasonId,
			List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod,
			List<Optional<TimeTableSpecial>> timetableSpecialInfo) {
		// 既に授業があるかないか、その情報を格納
		List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = new ArrayList<>();

		// 講習日程取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = getDateList(specialSeasonId);

		int dateListIdx = 0;
		// 日ごとに、loopのコマ数に授業があるかどうか、チェック（画面に表示する内容が授業内容か、チェックボックスか決める）
		for (Optional<SpecialSeasonDateList> certainDate : specialSeasonDateList) {
			// 日付ごとに、授業があるか判定
			List<Optional<StudentScheduleSpecial>> eachDateClass = judgeExistClassTargetDate(filteringClassesByPeriod,
					certainDate);

			// loopのコマ数の値と日付情報を利用して、time_table_specialのIDを取得
			if (eachDateClass.size() == 0) {
				divideExistClassOrNotList
						.add(Optional.ofNullable(new StudentScheduleSpecial(certainDate.get().getClassDate(),
								new TimeTableSpecial(timetableSpecialInfo.get(dateListIdx).get().getId()))));
			} else {
				divideExistClassOrNotList.add(eachDateClass.get(0));
			}
			dateListIdx++;
		}
		return divideExistClassOrNotList;
	}

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param filteringClassesByPeriod コマ数ごとの授業予定
	 * @param certainDate              講習会の1日程
	 * @return eachDateClass 授業の情報
	 *
	 */
	private List<Optional<StudentScheduleSpecial>> judgeExistClassTargetDate(
			List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod,
			Optional<SpecialSeasonDateList> certainDate) {
		List<Optional<StudentScheduleSpecial>> eachDateClass = filteringClassesByPeriod.stream().filter(s -> {
			int result = s.get().getClassDate().compareTo(certainDate.get().getClassDate());
			// 「同じ日だと、0を返す」0がどうか判定
			int numberJudgeSameDay = 0;
			if (result == numberJudgeSameDay) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		return eachDateClass;
	}

	// 日付一覧取得
	private List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
		return specialSeasonDateList;
	}

}
