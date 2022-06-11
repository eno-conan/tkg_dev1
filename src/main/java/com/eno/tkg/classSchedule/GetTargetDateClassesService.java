package com.eno.tkg.classSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
class GetTargetDateClassesService {
	
	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;
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
		List<StudentScheduleNormal> classes = studentScheduleNormalRepository.findAllByClassDateOrderByTimeTableNormalAsc(date);
		// 整形
		List<Map<String, Object>> returnJsonLiteral = prepareClassInfo(classes);
		// 文字列変換
		String strJson = getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	// 授業一覧画面表示用に整形
	private List<Map<String, Object>> prepareClassInfo(List<StudentScheduleNormal> classes) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal eachClass : classes) {
			Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
			// TODO:振替授業である場合に、その情報を画面に表示しておいた方がいいかと・・
			// 理想は、いつからの振替かだが、振替期限から分かるか
			eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
			eachRowInfoMap.put("period", eachClass.getTimeTableNormal().getPeriod());
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


	private String dateToDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	// 取得データをJson形式にする
	private String getDataToJsonFormat(Object returnJsonLiteral) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String strJson = "";
		strJson = mapper.writeValueAsString(returnJsonLiteral);
		return strJson;
	}
}
