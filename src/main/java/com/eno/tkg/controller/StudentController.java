package com.eno.tkg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.service.StudentSpecialScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
public class StudentController {

	@Autowired
	private StudentSpecialScheduleService studentSpecialScheduleService;

	@GetMapping("/student/current-special-schedule/{studentId}")
	public String getTargetStudentSpecialSchedule(@PathVariable(name = "studentId") final String studentId) {
		try {
			return studentSpecialScheduleService.getTargetStudentSpecialSchedule(studentId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

}
