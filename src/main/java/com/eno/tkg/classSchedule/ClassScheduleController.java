package com.eno.tkg.classSchedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
public class ClassScheduleController {

	@Autowired
	private GetTargetDateClassesService getTargetDateClassesService;

	@Autowired
	private GetSelectStudentClassesService getSelectStudentClassesService;

	@Autowired
	private UpdateSelectClassScheduleService updateSelectClassScheduleService;

	@GetMapping("/class-schedule")
	public String getTargetDateClassSchedule(@RequestParam(name = "targetDate") final String dateStr) {
		Date date = ClassScheduleUtil.convertStrDateToDateType(dateStr);
		try {
			return getTargetDateClassesService.getTargetDateClassSchedule(date);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@GetMapping("/class-schedule/student-schedule/{studentId}")
	public String getStudentClassSchedule(@PathVariable(name = "studentId") final String studentId) {
		try {
			return getSelectStudentClassesService.getSelectStudentClassSchedule(studentId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@PutMapping("/class-schedule/update")
	public StudentScheduleNormal updateTargetClassSchedule(@RequestBody final String content) {
		try {
			return updateSelectClassScheduleService.updateTargetClassSchedule(content);
		} catch (Exception e) {
			return new StudentScheduleNormal(e.getMessage());
		}
	}

}
