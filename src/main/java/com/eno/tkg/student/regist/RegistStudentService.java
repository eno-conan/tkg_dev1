package com.eno.tkg.student.regist;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.repository.master.ClassroomRepository;
import com.eno.tkg.repository.master.GradeRepository;
import com.eno.tkg.repository.master.StudentRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class RegistStudentService {

	@Autowired
	private StudentRepository studentRepository;

	/**
	 * 生徒登録
	 * 
	 * @param content 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	@Transactional
	String registStudent(final String content) throws JsonProcessingException {
		System.out.println(content);
		String[] requestBoby = content.split(",");
		List<String> requestBodyList = Arrays.asList(requestBoby);

		Student insertInfo = new Student();
		insertInfo.setStudentName(requestBodyList.get(0));
		insertInfo.setBirthday(UseOverFunction.convertStrDateToDateType(requestBodyList.get(1)));
		insertInfo.setClassroom(new Classroom(Integer.parseInt(requestBodyList.get(2))));
		insertInfo.setGrade(new Grade(requestBodyList.get(3)));
		insertInfo.setDeleteFlg(false);
		insertInfo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		insertInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		studentRepository.save(insertInfo);
		String strJson = UseOverFunction.getDataToJsonFormat("生徒登録処理が完了しました");
		return strJson;
	}


}
