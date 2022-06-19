package com.eno.tkg.student.specialSchedule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.StudentClassSpecialSummaryRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.TimeTableSpecialRepository;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetStudentSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private StudentClassSpecialSummaryRepository studentClassSpecialSummaryRepository;

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

		// 講習日程取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = getDateList(specialSeasonId);

		// 生徒スケジュール取得
		List<Optional<StudentScheduleSpecial>> studentSchedule = studentScheduleSpecialRepository
				.findByStudentOrderByClassDate(new Student(Integer.parseInt(studentId)));

		// フロントに返す全授業情報（コマごとに情報を格納）
		Map<String, List<Map<String, Object>>> classesGropingPeriodMap = new LinkedHashMap<>();

		// 2コマから8コマまで
		for (int period = 2; period < 4; period++) {
			String currentPeriod = String.valueOf(period);

			// コマごとに情報のフィルタリング
			List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod = studentSchedule.stream()
					.filter(s -> s.get().getTimeTableSpecial().getPeriod().equals(currentPeriod))
					.collect(Collectors.toList());
//			System.out.println(currentPeriod + ":" + filteringClassesByPeriod.size() + "classes");

			//
			List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = new ArrayList<>();

			List<Optional<TimeTableSpecial>> timetableSpecialInfo = timeTableSpecialRepository
					.findByPeriod(currentPeriod);

			int dateListIdx = 0;
			for (Optional<SpecialSeasonDateList> certainDate : specialSeasonDateList) {
				// 日付ごとに
				List<Optional<StudentScheduleSpecial>> eachDateClass = filteringClassesByPeriod.stream().filter(s -> {
					int result = s.get().getClassDate().compareTo(certainDate.get().getClassDate());
					if (result == 0) {
						return true;
					}
					return false;
				}).collect(Collectors.toList());

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

			// 整形した情報を格納するためのList
			List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
			for (Optional<StudentScheduleSpecial> eachClass : divideExistClassOrNotList) {
				Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();

				eachRowInfoMap.put("id",
						eachClass.get().getId() == null ? "" : String.valueOf(eachClass.get().getId()));
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

				returnJsonLiteral.add(eachRowInfoMap);
			}

			classesGropingPeriodMap.put(currentPeriod, returnJsonLiteral);
		}
		String strJson = UseOverFunction.getDataToJsonFormat(classesGropingPeriodMap);
		return strJson;
	}

	private List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
		// 日付一覧取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
		return specialSeasonDateList;
	}

}
