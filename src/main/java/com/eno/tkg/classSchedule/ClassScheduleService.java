package com.eno.tkg.classSchedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	 * @param date 情報を取得したい日付
	 * @return json 授業情報
	 *
	 */
	String getTargetDateClassSchedule(final Date date) {
		// 本日の授業に関する情報取得
		List<StudentScheduleNormal> classes = studentScheduleNormalRepository.findAllByClassDate(date);

		//整形
		List<Map<String, String>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal eachClass : classes) {
			Map<String, String> eachRowInfoMap = new LinkedHashMap<>();
			eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
			eachRowInfoMap.put("period", eachClass.getTimeTableNormal().getPeriod());
			eachRowInfoMap.put("grade", eachClass.getStudent().getGrade().getDisplayName());
			eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
			eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
			eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());

			returnJsonLiteral.add(eachRowInfoMap);
		}
		ObjectMapper mapper = new ObjectMapper();
		String strJson = "";
		//文字列変換
		try {
			strJson = mapper.writeValueAsString(returnJsonLiteral);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return strJson;
	}

	public String updateTargetClassSchedule(final String content) {
		System.out.println(content);
		return "";
	}

}
