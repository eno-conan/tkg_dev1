package com.eno.tkg.student.subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.repository.master.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistStudentSubjectService {

	@Autowired
	private StudentRepository studentRepository;

	/**
	 * 生徒科目登録
	 * 
	 * @param content 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	String registStudentSubject(final String content) throws JsonProcessingException {
		String[] requestBoby = content.split(",");
		List<String> requestBodyList = Arrays.asList(requestBoby);
		return "";
	}

}
