package com.eno.tkg.student.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentSubject;
import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.exception.StudentSubjectException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class SubjectController {

	@Autowired
	private GetStudentSubjectService getStudentSubjectService;

	@Autowired
	private RegistStudentSubjectService registStudentSubjectService;

	@Autowired
	private SubjectPrepareService registStudentSubjectPrepareService;

	/**
	 * チェックした生徒の受講科目取得
	 */
	@GetMapping("/student/search-subject/{studentId}")
	String getStudentSubject(@PathVariable(name = "studentId") final String studentId) {

		try {
			return getStudentSubjectService.getStudentSubject(studentId);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (StudentSubjectException e) {
			return e.getMessage();
		}
	}

	/**
	 * 該当生徒学年に基づいて、科目候補を取得
	 */
	@GetMapping("/student/regist-subject-prepare-grade/{studentId}")
	String prepareDataSubjectByGrade(@PathVariable(name = "studentId") final String studentId) {

		try {
			return registStudentSubjectPrepareService.prepareDataSubjectByGrade(studentId);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (RegistStudentException e) {
			return e.getMessage();
		}
	}

	/**
	 * 該当生徒学年に基づいて、講師候補を取得
	 */
	@GetMapping("/student/regist-subject-prepare-lecturer/{studentId}")
	String prepareDataLecturer(@PathVariable(name = "studentId") final String studentId) {

		try {
			return registStudentSubjectPrepareService.prepareDataLecurer(studentId);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (RegistStudentException e) {
			return e.getMessage();
		}
	}

	/**
	 * 該当生徒学年に基づいて、科目候補を取得
	 */
	@GetMapping("/student/regist-subject-prepare-timetable/{studentId}")
	String prepareDataTimeTableNormal(@PathVariable(name = "studentId") final String studentId) {

		try {
			return registStudentSubjectPrepareService.prepareDataTimeTable(studentId);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (RegistStudentException e) {
			return e.getMessage();
		}
	}

	/**
	 * 科目登録
	 */
	@PostMapping("/student/regist-subject")
	StudentSubject registStudent(@RequestBody final String content) {
		try {
			return registStudentSubjectService.registStudentSubject(content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new StudentSubject(e.getMessage());
		}
	}

}
