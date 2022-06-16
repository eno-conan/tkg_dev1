package com.eno.tkg.student;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentScheduleSpecial;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class StudentController {

	@Autowired
	private StudentSpecialScheduleService studentSpecialScheduleService;

	@Autowired
	private GetStudentSpecialScheduleService getStudentSpecialScheduleService;

	@Autowired
	private UpdateSpecialScheduleService updateSpecialScheduleService;

	@Autowired
	private RegistStudentService registStudentService;

	/**
	 * 生徒登録に必要なデータ取得（教室情報）
	 */
	@GetMapping("/student/regist-prepare-classroom")
	String prepareDataClassroomRegistStudent() {
		try {
			return registStudentService.prepareDataClassroomRegistStudent();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 生徒登録に必要なデータ取得（学年情報）
	 */
	@GetMapping("/student/regist-prepare-grade")
	String prepareDataGradeRegistStudent() {
		try {
			return registStudentService.prepareDataGradeRegistStudent();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 生徒登録
	 */
	@PostMapping("/student/regist")
	String registStudent(@RequestBody final String content) {
		try {
			return registStudentService.registStudent(content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 講習期間の日付取得
	 */
	@GetMapping("/student/special-date-list/{specialSeasonId}")
	String etSpecialDateList(@PathVariable(name = "specialSeasonId") final String specialSeasonId) {
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
	String getTargetStudentSpecialSchedule(@PathVariable(name = "studentId") final String studentId,
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
	String getTargetStudentSpecialSummary(@PathVariable(name = "studentId") final String studentId,
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
	 * @throws Exception
	 * 
	 */
	@PutMapping("/student/update-special-schedule")
	String updateTargetStudentSpecialSchedule(@RequestBody final String content) throws Exception {
		try {
			try {
				return updateSpecialScheduleService.updateTargetStudentSpecialSchedule(content);
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new Exception("生徒スケジュール更新：SQL更新でエラーが発生しました");
			}
		} catch (JsonProcessingException e) {
			return "";
		} catch (Exception e) {
			return "";
		}

	}
}
