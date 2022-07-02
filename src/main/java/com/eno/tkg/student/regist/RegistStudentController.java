package com.eno.tkg.student.regist;

import java.util.List;

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
class RegistStudentController {

	@Autowired
	private RegistStudentService registStudentService;
	
	@Autowired
	private PrepareService registStudentPrepareService;

	/**
	 * 生徒登録に必要なデータ取得（教室情報）
	 */
	@GetMapping("/student/regist-prepare-classroom")
	String prepareDataClassroomRegistStudent() {
		try {
			return registStudentPrepareService.prepareDataClassroomRegistStudent();
		} catch (JsonProcessingException e) {
			return e.getMessage();
		} catch (RegistStudentException e) {
			return e.getMessage();
		}
	}

	/**
	 * 生徒登録に必要なデータ取得（学年情報）
	 */
	@GetMapping("/student/regist-prepare-grade")
	String prepareDataGradeRegistStudent() {
		try {
			return registStudentPrepareService.prepareDataGradeRegistStudent();
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
}
