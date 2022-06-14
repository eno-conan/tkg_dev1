package com.eno.tkg.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private GetStudentSpecialScheduleService getStudentSpecialScheduleService;

	@Autowired
	private UpdateSpecialScheduleService updateSpecialScheduleService;

	/**
	 * 講習期間の日付取得
	 */
	@GetMapping("/student/special-date-list/{specialSeasonId}")
	public String etSpecialDateList(@PathVariable(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return studentSpecialScheduleService.getSpecialDateList(specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * 講習期間の{studentId}のスケジュール取得
	 * 
	 */
	@GetMapping("/student/special-schedule/{studentId}")
	public String getTargetStudentSpecialSchedule(@PathVariable(name = "studentId") final String studentId,
			@RequestParam(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return getStudentSpecialScheduleService.getTargetStudentSpecialSchedule(studentId, specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * 講習期間の{studentId}の受講科目とコマ数情報取得
	 * 
	 */
	@GetMapping("/student/special-summary/{studentId}")
	public String getTargetStudentSpecialSummary(@PathVariable(name = "studentId") final String studentId,
			@RequestParam(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return studentSpecialScheduleService.getTargetStudentSpecialSummary(studentId, specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";

		}

	}

	/**
	 * スケジュール更新（追加のみ、削除はまだ）
	 * 
	 */
	@PutMapping("/student/update-special-schedule")
	public String updateTargetStudentSpecialSchedule(@RequestBody final String content) {
		try {
			return updateSpecialScheduleService.updateTargetStudentSpecialSchedule(content);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
