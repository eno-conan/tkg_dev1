package com.eno.tkg.student;

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
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.repository.StudentClassSpecialSummaryRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class UpdateSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private StudentClassSpecialSummaryRepository studentClassSpecialSummaryRepository;

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	/**
	 * // 生徒の講習期間のスケジュール更新
	 * 
	 * @param studentId 生徒ID
	 * @return json コマごとに整形した授業予定一覧
	 * @throws JsonProcessingException
	 *
	 */
	// 戻り値はMapを使用
	public String updateTargetStudentSpecialSchedule(final String content)
			throws JsonProcessingException {
	System.out.println(content);
		String studentId = content.split(",")[0];
		String specialSeasonId = content.split(",")[1];
		String subjectId = content.split(",")[2];//このID値から、科目名取得をすること
		StudentScheduleSpecial tmp = new StudentScheduleSpecial();
		
		tmp.setStudent(new Student(Integer.parseInt(studentId)));
		tmp.setSubject(new Subject("m3"));
		tmp.setLecturer(new Lecturer(1));
//		tmp.setTimeTableSpecial(new());
		String strJson = UseOverFunction.getDataToJsonFormat("");
		return strJson;
	}

//	private List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
//		// 日付一覧取得
//		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
//				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
//		return specialSeasonDateList;
//	}

}
