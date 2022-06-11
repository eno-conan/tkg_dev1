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
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
class GetSelectStudentClassesService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	/**
	 * // チェックをいれた授業の生徒の授業予定を取得
	 * 
	 * @param content 予定取得に必要な情報
	 * @return json 授業予定
	 * @throws JsonProcessingException
	 *
	 */
	String getSelectStudentClassSchedule(final String studentId) throws JsonProcessingException {
		Integer idToInt = Integer.parseInt(studentId);
		List<StudentScheduleNormal> studentSchedule = studentScheduleNormalRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(idToInt), new Date());
		List<Map<String, Object>> returnJsonLiteral = prepareStudentScheduleInfo(studentSchedule);
		String strJson = getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	// 文字列型の日付をDate型に変更
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
	
	// 生徒予定表示用に整形
	private List<Map<String, Object>> prepareStudentScheduleInfo(List<StudentScheduleNormal> studentSchedule) {
		List<Map<String, Object>> returnJsonLiteral = setEachClassInfoToMap(studentSchedule);
		return Collections.unmodifiableList(returnJsonLiteral);
	}

	//画面表示用に、Map生成
	private List<Map<String, Object>> setEachClassInfoToMap(List<StudentScheduleNormal> studentSchedule) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal eachClass : studentSchedule) {
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

			String classDate = dateToDateStr(eachClass.getClassDate());
			eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
//				String rescheduleDateStart = dateToDateStr(eachClass.getRescheduleDateStart());
//				String rescheduleDateEnd = dateToDateStr(eachClass.getRescheduleDateLast());
//				eachRowInfoMap.put("rescheduleDateStart", rescheduleDateStart.replace("-", "/"));
//				eachRowInfoMap.put("rescheduleDateEnd", rescheduleDateEnd.replace("-", "/"));
			returnJsonLiteral.add(eachRowInfoMap);
		}
		return returnJsonLiteral;
	}

	//日付を文字列型の日付に
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
