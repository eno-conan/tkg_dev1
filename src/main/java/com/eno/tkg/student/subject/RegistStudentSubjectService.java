package com.eno.tkg.student.subject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentSubject;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.repository.StudentSubjectRepository;
import com.eno.tkg.repository.master.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistStudentSubjectService {

	@Autowired
	private StudentSubjectRepository studentSubjectRepository;

	/**
	 * 生徒科目登録
	 * 
	 * @param content 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	StudentSubject registStudentSubject(final String content) throws JsonProcessingException {
		String[] requestBoby = content.split(",");
		List<String> requestBodyList = Arrays.asList(requestBoby);
		StudentSubject stuSubj = new StudentSubject();
		stuSubj.setStudent(new Student(Integer.parseInt(requestBodyList.get(0))));
		stuSubj.setSubject(new Subject(requestBodyList.get(1)));
		stuSubj.setTimeTableNormal(new TimeTableNormal(Integer.parseInt(requestBodyList.get(2))));
		stuSubj.setLecturer(new Lecturer(Integer.parseInt(requestBodyList.get(3))));
		stuSubj.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		stuSubj.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return studentSubjectRepository.save(stuSubj);
	}

}
