package com.eno.tkg.student.wholeSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.StudentScheduleWhole;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetWholeScheduleService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	private final int CLASS_DATE_INDEX = 0;
	private final int PERIOD_INDEX = 1;
	private final int NORMAL_SPECIAL_INDEX = 2;

	/**
	 * 通常期と講習期両方を合体して、スケジュール取得
	 * 
	 * @param studentId 生徒ID
	 * @throws JsonProcessingException
	 *
	 */
	String getWholeSchedule(String studentId) throws JsonProcessingException {
		String todayStr = UseOverFunction.getTodayStr();
		Date date = UseOverFunction.convertStrDateToDateType(todayStr);

		// 現在の授業情報取得
		List<Map<String, Object>> studentClassList = getCurrentScheduleBeforeSort(studentId, date);

		// 日付_コマ_通常・講習_ID(2022-07-04_7_normal_8)
		List<String> idAndClassdateAndType = new ArrayList<>();
		for (Map<String, Object> eachData : studentClassList) {
			idAndClassdateAndType.add(eachData.get("classDate") + "_" + eachData.get("period") + "_"
					+ eachData.get("normalSpecial") + "_" + eachData.get("id"));
		}
		// ソート実施
		Collections.sort(idAndClassdateAndType);

		// 並び替え後の授業予定を格納
		List<Map<String, Object>> sortedStudentClassList = sortByClassDateAndPeriod(studentClassList,
				idAndClassdateAndType);
		String strJson = UseOverFunction.getDataToJsonFormat(sortedStudentClassList);
		return strJson;
	}

	/**
	 * ソート処理
	 * 
	 * @param studentClassList      現在の授業情報
	 * @param idAndClassdateAndType ソートに必要な情報
	 * @throws JsonProcessingException
	 *
	 */
	private List<Map<String, Object>> sortByClassDateAndPeriod(List<Map<String, Object>> studentClassList,
			List<String> idAndClassdateAndType) {
		List<Map<String, Object>> sortedStudentClassList = new ArrayList<>();
		for (String uniqueValue : idAndClassdateAndType) {
			String[] uniqueValues = uniqueValue.split("_");
			String classDate = uniqueValues[CLASS_DATE_INDEX];
			String period = uniqueValues[PERIOD_INDEX];
			String normalSpecial = uniqueValues[NORMAL_SPECIAL_INDEX];
			// ソートに必要な情報でフィルターリング
			List<Map<String, Object>> matchData = studentClassList.stream()
					.filter(cls -> cls.get("classDate").equals(classDate) && cls.get("period").equals(period)
							&& cls.get("normalSpecial").equals(normalSpecial))
					.collect(Collectors.toList());
			if (matchData.isEmpty()) {
				continue;
			}
			sortedStudentClassList.add(matchData.get(0));
		}
		return sortedStudentClassList;
	}

	/**
	 * 現在のスケジュール取得
	 * 
	 * @param studentId 生徒ID
	 * @param date      今日の日付
	 * @return studentClassList 現在の授業情報
	 *
	 */
	private List<Map<String, Object>> getCurrentScheduleBeforeSort(String studentId, Date date) {
		// 通常スケジュール取得
		List<StudentScheduleNormal> scheduleNormal = studentScheduleNormalRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(Integer.parseInt(studentId)), date);
		// 講習スケジュール取得
		List<StudentScheduleSpecial> scheduleSpecial = studentScheduleSpecialRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(Integer.parseInt(studentId)), date);

		// 通常期
		List<Map<String, Object>> studentClassList = new ArrayList<>();
		for (StudentScheduleNormal schedule : scheduleNormal) {
			StudentScheduleWhole ssw = new StudentScheduleWhole(schedule.getId(), schedule.getStudent().getId(),
					schedule.getSubject().getSubjectKey(), schedule.getSubject().getDisplayName(),
					schedule.getLecturer().getId(), schedule.getTimeTableNormal().getId(), schedule.getClassDate(),
					schedule.getTimeTableNormal().getPeriod(), schedule.getRescheduleDateStart(),
					schedule.getRescheduleDateLast(), schedule.isRescheduleFlg(), "normal");
			studentClassList.add(ssw.returnSetValueMap());
		}
		// 講習期
		for (StudentScheduleSpecial schedule : scheduleSpecial) {
			StudentScheduleWhole ssw = new StudentScheduleWhole(schedule.getId(), schedule.getStudent().getId(),
					schedule.getSubject().getSubjectKey(), schedule.getSubject().getDisplayName(),
					schedule.getLecturer().getId(), schedule.getTimeTableSpecial().getId(), schedule.getClassDate(),
					schedule.getTimeTableSpecial().getPeriod(), schedule.getRescheduleDateStart(),
					schedule.getRescheduleDateLast(), schedule.isRescheduleFlg(), "special");
			studentClassList.add(ssw.returnSetValueMap());
		}
		return studentClassList;
	}

}
