package com.eno.tkg.student.wholeSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class WholeScheduleController {

	@Autowired
	private GetWholeScheduleService getService;

	/**
	 * 講習期間の日付取得
	 */
	@GetMapping("/student/schedule-whole/{studentId}")
	String getWholeSchedule(@PathVariable(name = "studentId") final String studentId) {
		try {
			return getService.getWholeSchedule(studentId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

}
