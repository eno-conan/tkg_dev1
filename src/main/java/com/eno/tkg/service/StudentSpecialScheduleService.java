package com.eno.tkg.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class StudentSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	// 戻り値はMapを使用
	public String getTargetStudentSpecialSchedule(final String studentId) throws JsonProcessingException {
		List<StudentScheduleSpecial> studentSchedule = studentScheduleSpecialRepository
				.findByStudentOrderByClassDate(new Student(Integer.parseInt(studentId)));

		// フロントに返す全授業情報（コマごとに情報を格納）
		Map<String, List<Map<String, Object>>> classesGropingPeriodMap = new LinkedHashMap<>();

		// 2コマから8コマまで
		for (int period = 2; period < 8; period++) {
			String currentPeriod = String.valueOf(period);

			// コマごとに情報のフィルタリング
			List<StudentScheduleSpecial> filteringClassesByPeriod = studentSchedule.stream()
					.filter(s -> s.getTimeTableSpecial().getPeriod().equals(currentPeriod))
					.collect(Collectors.toList());

			// 整形した情報を格納するためのList
			List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
			for (StudentScheduleSpecial eachClass : filteringClassesByPeriod) {
				Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
				// TODO:振替授業である場合に、その情報を画面に表示しておいた方がいいかと・・
				// 理想は、いつからの振替かだが、振替期限から分かるか
				eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
				eachRowInfoMap.put("studentId", String.valueOf(eachClass.getStudent().getId()));
				eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
				eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());
//				eachRowInfoMap.put("period", eachClass.getTimeTableSpecial().getPeriod());

				String classDate = UseOverFunction.dateToDateStr(eachClass.getClassDate());
				eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
				returnJsonLiteral.add(eachRowInfoMap);
			}

//			System.out.println("Period:" + period + ", Planclasses:" + filteringClassesByPeriod.size());
			classesGropingPeriodMap.put(currentPeriod, returnJsonLiteral);
		}
		String strJson = UseOverFunction.getDataToJsonFormat(classesGropingPeriodMap);
		return strJson;
	}

}
