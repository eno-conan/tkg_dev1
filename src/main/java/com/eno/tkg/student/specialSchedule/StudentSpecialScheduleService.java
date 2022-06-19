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
import com.eno.tkg.repository.StudentClassSpecialSummaryRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
class StudentSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private StudentClassSpecialSummaryRepository studentClassSpecialSummaryRepository;

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	/**
	 * // 講習期間の日程を取得
	 * 
	 * @param specialSeasonId 講習ID
	 * @return json 日程一覧
	 * @throws JsonProcessingException
	 *
	 */
	String getSpecialDateList(final String specialSeasonId) throws JsonProcessingException {

		// 日付取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = getDateList(specialSeasonId);

		// 整形後の値を格納
		List<String> dateList = new ArrayList<>();
		for (Optional<SpecialSeasonDateList> date : specialSeasonDateList) {
			dateList.add(UseOverFunction.dateToDateStr(date.get().getClassDate()).replace("-", "/"));
		}
		String strJson = UseOverFunction.getDataToJsonFormat(dateList);
		return strJson;
	}

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param studentId 生徒ID
	 * @return json 科目とコマ数
	 * @throws JsonProcessingException
	 *
	 */
	String getTargetStudentSpecialSummary(final String studentId, final String specialSeasonId) throws JsonProcessingException {
		List<Optional<StudentClassSpecialSummary>> studentSpecialSummary = studentClassSpecialSummaryRepository
				.findBySpecialSeasonAndStudent(new SpecialSeason(Integer.parseInt(specialSeasonId)),
						new Student(Integer.parseInt(studentId)));
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (Optional<StudentClassSpecialSummary> eachSummary : studentSpecialSummary) {
			Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
			eachRowInfoMap.put("id", String.valueOf(eachSummary.get().getId()));
			eachRowInfoMap.put("studentId", String.valueOf(eachSummary.get().getStudent().getId()));
			eachRowInfoMap.put("subjectName", eachSummary.get().getSubject().getDisplayName());
			eachRowInfoMap.put("totalClassCount", String.valueOf(eachSummary.get().getTotalClassCount()));
			eachRowInfoMap.put("unplaceClassCount", String.valueOf(eachSummary.get().getUnplaceClassCount()));
			returnJsonLiteral.add(eachRowInfoMap);
		}
		String strJson = UseOverFunction.getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	private List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
		// 日付一覧取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
		return specialSeasonDateList;
	}

}
