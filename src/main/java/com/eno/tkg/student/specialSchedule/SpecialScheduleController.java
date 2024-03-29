package com.eno.tkg.student.specialSchedule;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.exception.UpdateSpecialScheduleException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class SpecialScheduleController {

	@Autowired
	private GetSummaryService studentSpecialScheduleService;

	@Autowired
	private GetClassesService getStudentSpecialScheduleService;

	@Autowired
	private UpdateSpecialScheduleService updateSpecialScheduleService;

	/**
	 * 講習期間の日付取得
	 */
	@GetMapping("/student/date-list-special/{specialSeasonId}")
	String etSpecialDateList(@PathVariable(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return studentSpecialScheduleService.getSpecialDateList(specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * 講習期間の{studentId}の受講科目とコマ数情報取得
	 * 
	 */
	@GetMapping("/student/summary-special/{studentId}")
	String getTargetStudentSpecialSummary(@PathVariable(name = "studentId") final String studentId,
			@RequestParam(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return studentSpecialScheduleService.getTargetStudentSpecialSummary(studentId, specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";

		}
	}

	/**
	 * 講習期間の{studentId}のスケジュール取得
	 * 
	 */
	@GetMapping("/student/schedule-special/{studentId}")
	String getTargetStudentSpecialSchedule(@PathVariable(name = "studentId") final String studentId,
			@RequestParam(name = "specialSeasonId") final String specialSeasonId) {
		try {
			return getStudentSpecialScheduleService.getTargetStudentSpecialSchedule(studentId, specialSeasonId);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * スケジュール更新
	 * 
	 * @throws Exception
	 * 
	 */
	@PutMapping("/student/schedule-special")
	String updateTargetStudentSpecialSchedule(@RequestBody final String content) throws Exception {
		try {
			try {
				return updateSpecialScheduleService.updateTargetStudentSpecialSchedule(content);
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new Exception("生徒スケジュール更新：SQL更新でエラーが発生しました");
			}
		} catch (JsonProcessingException e) {
			return "";
		} catch (UpdateSpecialScheduleException e) {
			return e.getMessage();
		}

	}
}
