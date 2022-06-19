//package com.eno.tkg.student;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.eno.tkg.exception.RegistStudentException;
//import com.eno.tkg.exception.StudentSubjectException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/tkg")
//class StudentController2 {
//
//	@Autowired
//	private SearchStudentService searchStudentService;
//	
//	@Autowired
//	private GetStudentSubjectService getStudentSubjectService;
//	
//	@Autowired
//	private RegistStudentSubjectService registStudentSubjectService;
//
//	/**
//	 * 該当生徒の情報取得
//	 */
//	@GetMapping("/student/search")
//	String searchStudent(@RequestParam(name = "classroomId") final String classroomId,
//			@RequestParam(name = "studentName") final String studentName) {
//		try {
//			return searchStudentService.searchStudent(classroomId, studentName);
//		} catch (JsonProcessingException e) {
//			return e.getMessage();
//		} catch (RegistStudentException e) {
//			return e.getMessage();
//		}
//	}
//
//	/**
//	 * 該当生徒の受講科目取得
//	 */
//	@GetMapping("/student/search-subject/{studentId}")
//	String getStudentSubject(@PathVariable(name = "studentId") final String studentId) {
//		
//		try {
//			return getStudentSubjectService.getStudentSubject(studentId);
//		} catch (JsonProcessingException e) {
//			return e.getMessage();
//		} catch (StudentSubjectException e) {
//			return e.getMessage();
//		}
//	}
//	
//	/**
//	 * 該当生徒学年に基づいて、科目候補を取得
//	 */
//	@GetMapping("/student/regist-subject-prepare-grade/{studentId}")
//	String prepareDataSubjectByGrade(@PathVariable(name = "studentId") final String studentId) {
//		
//		try {
//			return registStudentSubjectService.prepareDataSubjectByGrade(studentId);
//		} catch (JsonProcessingException e) {
//			return e.getMessage();
//		} catch (RegistStudentException e) {
//			return e.getMessage();
//		}
//	}
//	
//	/**
//	 * 該当生徒学年に基づいて、科目候補を取得
//	 */
//	@GetMapping("/student/regist-subject-prepare-lecturer/{studentId}")
//	String prepareDataLecturer(@PathVariable(name = "studentId") final String studentId) {
//		
//		try {
//			return registStudentSubjectService.prepareDataLecurer(studentId);
//		} catch (JsonProcessingException e) {
//			return e.getMessage();
//		} catch (RegistStudentException e) {
//			return e.getMessage();
//		}
//	}
//	
//	/**
//	 * 該当生徒学年に基づいて、科目候補を取得
//	 */
//	@GetMapping("/student/regist-subject-prepare-timetable")
//	String prepareDataTimeTableNormal() {
//		
//		try {
//			return registStudentSubjectService.prepareDataTimeTable();
//		} catch (JsonProcessingException e) {
//			return e.getMessage();
//		} catch (RegistStudentException e) {
//			return e.getMessage();
//		}
//	}
//}
