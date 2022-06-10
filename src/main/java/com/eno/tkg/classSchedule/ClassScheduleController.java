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
	private ClassScheduleService classScheduleService;

	@GetMapping("/classSchedule")
	public String getTargetDateClassSchedule(@RequestParam(name = "targetDate") final String dateStr) {
		Date date = classScheduleService.convertStrDateToDateType(dateStr);
		try {
			return classScheduleService.getTargetDateClassSchedule(date);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@GetMapping("/classSchedule/studentSchedule/{studentId}")
	public String getStudentClassSchedule(@PathVariable(name = "studentId") final String studentId) {
		try {
			return classScheduleService.getSelectStudentClassSchedule(studentId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@PutMapping("/classSchedule/update")
	public StudentScheduleNormal updateTargetClassSchedule(@RequestBody final String content) {
		try {
			return classScheduleService.updateTargetClassSchedule(content);
		} catch (Exception e) {
			return new StudentScheduleNormal(e.getMessage());
		}
	}

}
