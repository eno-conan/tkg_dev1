package com.eno.tkg.student;

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

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	String prepareDataClassroomRegistStudent() throws Exception {
		List<Classroom> allClassroomInfo = classroomRepository.findAll();
		if (allClassroomInfo.isEmpty()) {
			log.error("教室情報が0件の状態");
			throw new Exception("教室情報に関するエラーが発生しました");
		}

		// 教室IDと教室名のみ取得
		List<Map<String, String>> classroomIdAndNameList = pickupClassroomInfo(allClassroomInfo);
		String strJson = UseOverFunction.getDataToJsonFormat(classroomIdAndNameList);
		return strJson;
	}

	String prepareDataGradeRegistStudent() throws Exception {
		List<Grade> allGradeInfo = gradeRepository.findAll();
		if (allGradeInfo.isEmpty()) {
			log.error("学年情報が0件の状態");
			throw new Exception("学年情報に関するエラーが発生しました");
		}

		List<Map<String, String>> gradekeyAndNameList = pickupGradeInfo(allGradeInfo);
		String strJson = UseOverFunction.getDataToJsonFormat(gradekeyAndNameList);
		return strJson;
	}

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

	private List<Map<String, String>> pickupClassroomInfo(List<Classroom> allClassroomInfo) {
		List<Map<String, String>> returnJsonLiteral = new ArrayList<>();
		for (Classroom info : allClassroomInfo) {
			Map<String, String> classroomIdAndNameMap = new LinkedHashMap<>();
			classroomIdAndNameMap.put("id", String.valueOf(info.getId()));
			classroomIdAndNameMap.put("name",
					info.getMPrefecture().getPrefectureName() + " | " + info.getClassroomName());
			returnJsonLiteral.add(classroomIdAndNameMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

	private List<Map<String, String>> pickupGradeInfo(List<Grade> allGradeInfo) {
		List<Map<String, String>> returnJsonLiteral = new ArrayList<>();
		for (Grade info : allGradeInfo) {
			Map<String, String> gradekeyAndNameMap = new LinkedHashMap<>();
			gradekeyAndNameMap.put("key", info.getGradeKey());
			gradekeyAndNameMap.put("name", info.getDisplayName());
			returnJsonLiteral.add(gradekeyAndNameMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
