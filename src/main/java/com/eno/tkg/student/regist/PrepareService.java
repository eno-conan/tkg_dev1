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
class PrepareService {

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	/**
	 * // 教室情報取得
	 * 
	 * @return json 整形した教室情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataClassroomRegistStudent() throws RegistStudentException, JsonProcessingException {
		List<Classroom> allClassroomInfo = classroomRepository.findAll();
		if (allClassroomInfo.isEmpty()) {
			log.error("教室情報が0件の状態");
			throw new RegistStudentException("教室情報取得でエラーが発生しました。少々お待ちください。");
		}

		// 教室IDと教室名のみ取得
		List<Map<String, String>> classroomIdAndNameList = pickupClassroomInfo(allClassroomInfo);
		String strJson = UseOverFunction.getDataToJsonFormat(classroomIdAndNameList);
		return strJson;
	}

	/**
	 * // 学年情報取得
	 * 
	 * @return json 整形した学年情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataGradeRegistStudent() throws RegistStudentException, JsonProcessingException {
		List<Grade> allGradeInfo = gradeRepository.findAll();
		if (allGradeInfo.isEmpty()) {
			log.error("学年情報が0件の状態");
			throw new RegistStudentException("学年情報取得でエラーが発生しました。少々お待ちください。");
		}

		List<Map<String, String>> gradekeyAndNameList = pickupGradeInfo(allGradeInfo);
		String strJson = UseOverFunction.getDataToJsonFormat(gradekeyAndNameList);
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
