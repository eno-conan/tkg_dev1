package com.eno.tkg.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.exception.SearchStudentException;
import com.eno.tkg.repository.master.StudentRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchStudentService {

	@Autowired
	private StudentRepository studentRepository;

	/**
	 * // 生徒情報取得
	 * 
	 * @return json 整形した学年情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	public String searchStudent(final String classroomId, final String studentName) throws JsonProcessingException {
		List<Student> studentWholeInfo = studentRepository
				.findByClassroomAndStudentNameLike(new Classroom(Integer.parseInt(classroomId)), "%" + studentName + "%");
		if (studentWholeInfo.isEmpty()) {
			log.info("生徒情報が0件の状態");
//			throw new SearchStudentException("生徒情報取得でエラーが発生しました。少々お待ちください。");
			String strJson = UseOverFunction.getDataToJsonFormat("0");
			return strJson;
		}

//		// 教室IDと教室名のみ取得
		List<Map<String, Object>> studentIdAndNameList = pickupstudentInfo(
				Collections.unmodifiableList(studentWholeInfo));
		String strJson = UseOverFunction.getDataToJsonFormat(studentIdAndNameList);
		return strJson;
	}

	private List<Map<String, Object>> pickupstudentInfo(List<Student> studentWholeInfo) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (Student info : studentWholeInfo) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("studentId", info.getId());
			infoMap.put("studentName", info.getStudentName());
			infoMap.put("classroomName", info.getClassroom().getClassroomName());
			infoMap.put("prefectureName", info.getClassroom().getMPrefecture().getPrefectureName());
			returnJsonLiteral.add(infoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
