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

import com.eno.tkg.entity.StudentSubject;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.SubjectTargetGrade;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.exception.RegistStudentException;
import com.eno.tkg.exception.StudentSubjectException;
import com.eno.tkg.repository.SubjectTargetGradeRepository;
import com.eno.tkg.repository.TimeTableNormalRepository;
import com.eno.tkg.repository.master.LecturerRepository;
import com.eno.tkg.repository.master.StudentRepository;
import com.eno.tkg.repository.master.SubjectRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistStudentSubjectPrepareService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private SubjectTargetGradeRepository subjectTargetGradeRepository;

	@Autowired
	private LecturerRepository lecturerRepository;

	@Autowired
	private TimeTableNormalRepository timeTableNormalRepository;

	/**
	 * 生徒科目登録のデータ準備（学年に対応する科目一覧）
	 * 
	 * @param studentId 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataSubjectByGrade(final String studentId) throws JsonProcessingException {
		Optional<List<Student>> studentinfo = studentRepository.findByIdAndDeleteFlgFalse(Integer.parseInt(studentId));
		if (studentinfo.isEmpty()) {
			log.warn("既に在籍していない生徒の可能性あり");
			throw new StudentSubjectException("生徒情報取得でエラーが発生しました。少々お待ちください。");
		}
		//生徒の学年で受講できる科目、かつ、まだ受講登録していない科目のみ取得
		Optional<List<SubjectTargetGrade>> subjectsByGrade = subjectTargetGradeRepository
				.getSubjectsByGradeAndNotRegistered(studentId);
		if (subjectsByGrade.isEmpty()) {
			log.error("この学年が受講できる受講科目が存在しない");
			throw new StudentSubjectException("科目情報取得でエラーが発生しました。少々お待ちください。");
		}
		List<Map<String, Object>> subjectList = pickupSubjectInfo(Collections.unmodifiableList(subjectsByGrade.get()));
		String strJson = UseOverFunction.getDataToJsonFormat(subjectList);
		return strJson;
	}

	/**
	 * 生徒科目登録のデータ準備（講師情報）
	 * 
	 * @param studentId 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataLecurer(final String studentId) throws JsonProcessingException {
		Optional<List<Student>> studentinfo = studentRepository.findByIdAndDeleteFlgFalse(Integer.parseInt(studentId));
		if (studentinfo.isEmpty()) {
			log.warn("既に在籍していない生徒の可能性あり");
			throw new StudentSubjectException("生徒情報取得でエラーが発生しました。少々お待ちください。");
		}
		// 講師一覧取得（生徒が在籍する教室の講師を先に取得
		Optional<List<Lecturer>> lecturers = lecturerRepository
				.findLecturerStudentWithClassroomTop(studentinfo.get().get(0).getClassroom().getId());
		if (lecturers.isEmpty()) {
			log.error("講師が1人も存在しない");
			throw new StudentSubjectException("講師情報取得でエラーが発生しました。少々お待ちください。");
		}
		List<Map<String, Object>> subjectList = pickupLecturerInfo(Collections.unmodifiableList(lecturers.get()));
		String strJson = UseOverFunction.getDataToJsonFormat(subjectList);
		return strJson;
	}

	/**
	 * 生徒科目登録のデータ準備（タイムテーブル情報）
	 * 
	 * @param studentId 登録内容
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataTimeTable(final String studentId) throws JsonProcessingException {
		Optional<List<TimeTableNormal>> timeTableInfo = timeTableNormalRepository.getFramesTargetStudentNoClass(studentId);
		if (timeTableInfo.isEmpty()) {
			log.error("タイムテーブルデータが0件");
			throw new StudentSubjectException("科目情報取得でエラーが発生しました。少々お待ちください。");
		}

		List<Map<String, Object>> subjectList = pickupTimeTableInfo(Collections.unmodifiableList(timeTableInfo.get()));
		String strJson = UseOverFunction.getDataToJsonFormat(subjectList);
		return strJson;
	}

	/**
	 * 生徒科目登録のデータ整形（定期コマテーブル一覧）
	 * 
	 * @param subjectsByGrade 学年に紐づく科目一覧
	 * @return returnJsonLiteral 整形した情報
	 *
	 */
	private List<Map<String, Object>> pickupTimeTableInfo(List<TimeTableNormal> timeTableInfo) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (TimeTableNormal info : timeTableInfo) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("timeTableId", String.valueOf(info.getId()));
			infoMap.put("dateOfWeekFrame", info.getDayOfWeekJa() + info.getPeriod());
			returnJsonLiteral.add(infoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

	/**
	 * 生徒科目登録のデータ整形（講師一覧）
	 * 
	 * @param subjectsByGrade 学年に紐づく科目一覧
	 * @return returnJsonLiteral 整形した情報
	 *
	 */
	private List<Map<String, Object>> pickupLecturerInfo(List<Lecturer> lecturers) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (Lecturer info : lecturers) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("lecturerId", String.valueOf(info.getId()));
			infoMap.put("lecturerName", info.getLecturerName() + " | " + info.getClassroom().getClassroomName() + "教室");
			returnJsonLiteral.add(infoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

	/**
	 * 生徒科目登録のデータ準備整形（学年に対応する科目一覧）
	 * 
	 * @param subjectsByGrade 学年に紐づく科目一覧
	 * @return returnJsonLiteral 整形した情報
	 *
	 */
	private List<Map<String, Object>> pickupSubjectInfo(List<SubjectTargetGrade> subjectsByGrade) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (SubjectTargetGrade info : subjectsByGrade) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			System.out.println(info.getSubject().getSubjectKey());
			System.out.println(info.getSubject().getDisplayName());
			infoMap.put("subjectKey", info.getSubject().getSubjectKey());
			infoMap.put("subjectName", info.getSubject().getDisplayName());
			returnJsonLiteral.add(infoMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
