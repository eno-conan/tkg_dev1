package com.eno.tkg.student.subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentSubject;
import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.exception.SearchStudentException;
import com.eno.tkg.repository.StudentSubjectRepository;
import com.eno.tkg.repository.master.StudentRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetStudentSubjectService {

	@Autowired
	private StudentSubjectRepository studentSubjectRepository;

	/**
	 * // 生徒情報取得
	 * 
	 * @return json 整形した学年情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	public String getStudentSubject(final String studentId) throws JsonProcessingException {

		// 結果格納
		List<StudentSubject> studentSubjectInfo = studentSubjectRepository
				.findByStudent(new Student(Integer.parseInt(studentId)));
		if (studentSubjectInfo.isEmpty()) {
			log.info("生徒科目情報が0件の状態");
			return UseOverFunction.getDataToJsonFormat("0");
		}

//		// 教室IDと教室名のみ取得
		List<Map<String, Object>> studentSubjectList = pickupstudentSubjectInfo(
				Collections.unmodifiableList(studentSubjectInfo));
		String strJson = UseOverFunction.getDataToJsonFormat(studentSubjectList);
		return strJson;
	}

	private List<Map<String, Object>> pickupstudentSubjectInfo(List<StudentSubject> studentSubjectInfo) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentSubject info : studentSubjectInfo) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("studentId", info.getId());
			infoMap.put("subjectName", info.getSubject().getDisplayName());
			infoMap.put("lecturerName", info.getLecturer().getLecturerName());
			infoMap.put("dateOfweekFrame",
					info.getTimeTableNormal().getDayOfWeekJa() + info.getTimeTableNormal().getPeriod());
			returnJsonLiteral.add(infoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}
}
