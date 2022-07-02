package com.eno.tkg.student.wholeSchedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
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

	/**
	 * 通常期と講習期両方を合体して、スケジュール取得
	 * 
	 * @param studentId 生徒ID
	 * @throws JsonProcessingException
	 *
	 */
	public String getWholeSchedule(String studentId) throws JsonProcessingException {
		String dateStr = "2022-07-01";
		Date date = UseOverFunction.convertStrDateToDateType(dateStr);
		// 通常スケジュール取得
		List<StudentScheduleNormal> scheduleNormal = studentScheduleNormalRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(Integer.parseInt(studentId)), date);
		// 講習スケジュール取得
		List<StudentScheduleSpecial> scheduleSpecial = studentScheduleSpecialRepository
				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(Integer.parseInt(studentId)), date);

		// 合体
		// 型を統一してからソート

		// 通常期
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal schedule : scheduleNormal) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("id", schedule.getId());
			infoMap.put("studentId", schedule.getStudent().getId());
			infoMap.put("subjectKey", schedule.getSubject().getSubjectKey());
			infoMap.put("lecturerId", schedule.getLecturer().getId());
			infoMap.put("timeTableId", schedule.getTimeTableNormal().getId());
			infoMap.put("classDate", UseOverFunction.dateToDateStr(schedule.getClassDate()));
			infoMap.put("rescheduleDateStart", UseOverFunction.dateToDateStr(schedule.getRescheduleDateStart()));
			infoMap.put("rescheduleDateLast", UseOverFunction.dateToDateStr(schedule.getRescheduleDateLast()));
			infoMap.put("rescheduleFlg", schedule.isRescheduleFlg());
			infoMap.put("normalSpecial", "normal");
			returnJsonLiteral.add(infoMap);
		}
		// 講習期
		for (StudentScheduleSpecial schedule : scheduleSpecial) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("id", schedule.getId());
			infoMap.put("studentId", schedule.getStudent().getId());
			infoMap.put("subjectKey", schedule.getSubject().getSubjectKey());
			infoMap.put("lecturerId", schedule.getLecturer().getId());
			infoMap.put("timeTableId", schedule.getTimeTableSpecial().getId());
			infoMap.put("classDate", UseOverFunction.dateToDateStr(schedule.getClassDate()));
			infoMap.put("rescheduleDateStart", UseOverFunction.dateToDateStr(schedule.getRescheduleDateStart()));
			infoMap.put("rescheduleDateLast", UseOverFunction.dateToDateStr(schedule.getRescheduleDateLast()));
			infoMap.put("rescheduleFlg", schedule.isRescheduleFlg());
			infoMap.put("normalSpecial", "special");
			returnJsonLiteral.add(infoMap);
		}
		String strJson = UseOverFunction.getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

//	private Map<String, Object> setValuesToMap(StudentSchedule scheduleInfo){
////		for (StudentSchedule schedule : scheduleInfo) {
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////			infoMap.put("id", schedule.getId());
////		}
//		Map<String, Object> infoMap = new LinkedHashMap<>();
//	}

}
