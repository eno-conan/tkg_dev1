package com.eno.tkg.student.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.exception.StudentSubjectException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
class SearchStudentController {

	@Autowired
	private SearchStudentService searchStudentService;
	
	/**
	 * 条件に合致する生徒の情報取得
	 */
	@GetMapping("/student/search")
	String searchStudent(@RequestParam(name = "classroomId") final String classroomId,
			@RequestParam(name = "studentName") final String studentName) {
		try {
			return searchStudentService.searchStudent(classroomId, studentName);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (RegistStudentException e) {
			return e.getMessage();
		}
	}
	
	
}
