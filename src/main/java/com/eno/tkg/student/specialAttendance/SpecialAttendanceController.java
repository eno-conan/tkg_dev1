package com.eno.tkg.student.specialAttendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.exception.UpdateSpecialAttendanceException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class SpecialAttendanceController {

	@Autowired
	private UpdateSpecialAttendanceService updateSpecialAttendanceService;

	@Autowired
	private StudentSpecialAttendanceService studentSpecialAttendanceService;

	/**
	 * 講習期間の生徒の出欠予定取得
	 */
	@GetMapping("/student/current-attendance-special/{studentId}")
	String getCurrentAttendanceSpecial(@PathVariable(name = "studentId") final String studentId,
			@RequestParam(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return studentSpecialAttendanceService.getCurrentSpecialAttendance(studentId, specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * 講習会参加予定作成
	 * 
	 * @throws Exception
	 * 
	 */
	@PutMapping("/student/update-special-attendance")
	String updateTargetStudentSpecialAttendance(@RequestBody final String content) throws Exception {
		try {
			return updateSpecialAttendanceService.updateAttendance(content);
		} catch (JsonProcessingException e) {
			return "";
		} catch (UpdateSpecialAttendanceException e) {
			return e.getMessage();
		}

	}
}
