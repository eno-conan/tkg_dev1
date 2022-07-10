package com.eno.tkg.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.eno.tkg.util.UseOverFunction;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StudentScheduleWhole {

	Map<String, Object> infoMap;

	private Integer id;
	private Integer studentId;
	private String subjectKey;
	private String subjectName;
	private Integer lecturerId;
	private Integer TimeTableId;
	private Date classDate;
	private String period;
	private Date classDateOrigin;
	private Date rescheduleDateStart;
	private Date rescheduleDateLast;
	private boolean rescheduleFlg;
	private String normalSpecial;

	public StudentScheduleWhole(Integer id, Integer studentId, String subjectKey, String subjectName,
			Integer lecturerId, Integer timeTableId, Date classDate, String period, Date rescheduleDateStart,
			Date rescheduleDateLast, boolean rescheduleFlg, String normalSpecial) {
		infoMap = new LinkedHashMap<>();
		infoMap.put("id", String.valueOf(id));
		infoMap.put("studentId", String.valueOf(studentId));
		infoMap.put("subjectKey", subjectKey);
		infoMap.put("subjectName", subjectName);
		infoMap.put("lecturerId", String.valueOf(lecturerId));
		infoMap.put("timeTableId", String.valueOf(timeTableId));
		infoMap.put("classDate", UseOverFunction.dateToDateStr(classDate).replace("-", "/"));
		infoMap.put("period", period);// ソートのために必要
		infoMap.put("rescheduleDateStart", UseOverFunction.dateToDateStr(rescheduleDateStart).replace("-", "/"));
		infoMap.put("rescheduleDateLast", UseOverFunction.dateToDateStr(rescheduleDateLast).replace("-", "/"));
		infoMap.put("rescheduleFlg", rescheduleFlg);
		infoMap.put("normalSpecial", normalSpecial);
	}

	public Map<String, Object> returnSetValueMap() {
		return this.infoMap;
	}

}
