package com.eno.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.entity.Member;
import com.eno.entity.StudentScheduleNormal;
import com.eno.entity.StudentSubject;
import com.eno.entity.master.Area;
import com.eno.entity.master.Classroom;
import com.eno.entity.master.Grade;
import com.eno.entity.master.Lecturer;
import com.eno.entity.master.Prefecture;
import com.eno.entity.master.TimeTableNormal;
import com.eno.repository.LecturerRepository;
import com.eno.repository.StudentScheduleNormalRepository;
import com.eno.repository.AreaRepository;
import com.eno.repository.GradeRepository;
import com.eno.repository.StudentSubjectRepository;
import com.eno.repository.ClassroomRepository;

@Service
public class TkgService1 {

	@Autowired
	private GradeRepository mGradeRepository;

	@Autowired
	private AreaRepository mAreaRepository;

	@Autowired
	private LecturerRepository lecturerRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private StudentSubjectRepository studentSubjectRepository;

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	/**
	 * @return 全都道府県取得
	 *
	 */
	public List<Prefecture> findAllMArea() {
		List<Area> allAreas = mAreaRepository.findAll();
		List<Prefecture> allPrefecture = new ArrayList<Prefecture>();
		allAreas.forEach(area -> {
			area.getPrefectures().forEach(pre -> {
				allPrefecture.add(pre);
			});
		});
		return allPrefecture;
	}

	/**
	 * @return 全教室取得
	 *
	 */
	public List<Classroom> findAllClassroom() {
		List<Classroom> allClassroom = classroomRepository.findAll();
		allClassroom.forEach(classroom -> {
			classroom.getLecturers().forEach(lec -> {
				System.out.println("教室に属する講師:" + lec.getLecturerName());
			});
		});
		return allClassroom;
	}

	/**
	 * @return 全学年取得
	 *
	 */
	public void findAllMGrade() {
		List<Grade> allgrades = mGradeRepository.findAll();
		allgrades.forEach(grade -> {
			System.out.println(grade.getDisplayName());
		});
	}

	/**
	 * @return 全講師取得
	 *
	 */
	public List<Lecturer> findAllLecturer() {
//		Optional<Lecturer> lecturerById = lecturerRepository.findById(2);
//		List<Lecturer> allActiveLecturer = lecturerRepository.findAll();
		List<Lecturer> allActiveLecturer = lecturerRepository.findByDeleteFlg(false);
		allActiveLecturer.forEach(lecturer -> {
			System.out.println(lecturer.getLecturerName());
		});
		return allActiveLecturer;
	}

	/**
	 * @return 曜日毎の実施授業取得
	 *
	 */
	public List<StudentSubject> findAllStundentsSubjects() {
//		List<StudentSubject> allStudentsSubjects = studentSubjectRepository.findAll();
		List<TimeTableNormal> timeTableIdsOnMonday = setTargetTimeTableIds();
		List<StudentSubject> allStudentsSubjects = studentSubjectRepository
				.findByTimeTableNormalIn(timeTableIdsOnMonday);
		allStudentsSubjects.forEach(stuSubj -> {
			System.out.println(stuSubj.getSubject().getDisplayName());
		});
		return allStudentsSubjects;
	}

	/**
	 * @param allStudentsSubjects 指定曜日に実施する授業の情報
	 *
	 */
	public void insertClassesOnTargetDayOfWeek(List<StudentSubject> allStudentsSubjects) {
		List<StudentSubject> insertData = new ArrayList<>(Collections.unmodifiableList(allStudentsSubjects));
		insertData.forEach(data -> {
			// TODO: 挿入データ設定処理のクラス構成
			StudentScheduleNormal ssn = new StudentScheduleNormal();
			ssn.setStudent(data.getStudent());
			ssn.setSubject(data.getSubject());
			ssn.setLecturer(data.getLecturer());
//			日程設定
			setRelatedClassDate(ssn);
			ssn.setRescheduleFlg(false);
			ssn.setStatus(0);

			//テーブルに保存
			studentScheduleNormalRepository.save(ssn);
		});
	}

	private void setRelatedClassDate(StudentScheduleNormal ssn) {
		Date nowDate = new Date();
		// 2週間後に実施する授業の日程設定
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.WEEK_OF_MONTH, 2);
		Date d1 = calendar.getTime();
		ssn.setClassDate(d1);
		// 2週間後に実施する授業の月初の日程取得（振替候補初日）
		Calendar calendarForFirstDayOfMonth = Calendar.getInstance();
		calendarForFirstDayOfMonth.setTime(d1);
		int first = calendarForFirstDayOfMonth.getActualMinimum(Calendar.DATE);
		calendarForFirstDayOfMonth.set(Calendar.DATE, first);
		Date d2 = calendarForFirstDayOfMonth.getTime();
		ssn.setRescheduleDateStart(d2);
		//// 2週間後に実施する授業の振替期限日取得（振替最終日）
		calendar.add(Calendar.WEEK_OF_MONTH, 2);
		Date d3 = calendar.getTime();
		ssn.setRescheduleDateLast(d3);
	}

	// TODO: 別クラスから呼び出す形で実装したい
	private List<TimeTableNormal> setTargetTimeTableIds() {
		List<TimeTableNormal> timeTableIdsOnMonday = new ArrayList<>();
		timeTableIdsOnMonday.add(new TimeTableNormal(1));
		timeTableIdsOnMonday.add(new TimeTableNormal(2));
		timeTableIdsOnMonday.add(new TimeTableNormal(3));
		return Collections.unmodifiableList(timeTableIdsOnMonday);
	}

}
