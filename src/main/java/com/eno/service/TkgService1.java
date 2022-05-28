package com.eno.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.entity.ClassroomDirector;
import com.eno.entity.LecturerTeachSubject;
import com.eno.entity.LecturerWorkableTime;
import com.eno.entity.StudentScheduleNormal;
import com.eno.entity.StudentSubject;
import com.eno.entity.master.Area;
import com.eno.entity.master.Classroom;
import com.eno.entity.master.Employee;
import com.eno.entity.master.Grade;
import com.eno.entity.master.Lecturer;
import com.eno.entity.master.Prefecture;
import com.eno.entity.master.Subject;
import com.eno.entity.master.TimeTableNormal;
import com.eno.repository.LecturerRepository;
import com.eno.repository.LecturerTeachSubjectRepository;
import com.eno.repository.LecturerWorkableTimeRepository;
import com.eno.repository.StudentScheduleNormalRepository;
import com.eno.repository.AreaRepository;
import com.eno.repository.GradeRepository;
import com.eno.repository.StudentSubjectRepository;
import com.eno.repository.ClassroomRepository;
import com.eno.repository.EmployeeRepository;
import com.eno.repository.ClassroomDirectorRepository;

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

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ClassroomDirectorRepository classroomDirectorRepository;

	@Autowired
	private LecturerWorkableTimeRepository lecturerWorkableTimeRepository;

	@Autowired
	private LecturerTeachSubjectRepository lecturerTeachSubjectRepository;

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
	 * 教室登録
	 */
	public int registNewClassroom() {
		Prefecture mPrefecture = new Prefecture();
		mPrefecture.setId(1);
		// 作成日時、更新日時は同じ
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// TODO:インスタンス生成時の引数はフォームクラスにしたい。
		Classroom newClassroom = new Classroom(mPrefecture, "新宿", "東京都新宿区西新宿", false, timestamp, timestamp);
		classroomRepository.save(newClassroom);
		return 0;
	}

	/**
	 * 室長登録（社員が実施）※ポータルユーザによっては、実施できないようにすること。
	 */
	@Transactional
	public int registClassroomDirector() throws Exception {
		Classroom targetclassroom = new Classroom(1);
		Employee assignedEmployee = new Employee(1);
		// 作成日時、更新日時は同じ
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// TODO:インスタンス生成時の引数はフォームクラスにしたい。
		ClassroomDirector newClassroomDirector = new ClassroomDirector(targetclassroom, assignedEmployee, timestamp,
				timestamp);
		classroomDirectorRepository.save(newClassroomDirector);
		return 0;
	}

	/**
	 * 講師登録（社員が実施）※ポータルユーザによっては、実施できないようにすること。
	 */
	@Transactional
	public int registLecturer() throws Exception {
		Classroom targetclassroom = new Classroom(1);
		// 作成日時、更新日時は同じ
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// TODO:インスタンス生成時の引数はフォームクラスにしたい。
		Lecturer newLecturer = new Lecturer(targetclassroom, "a", new Date(), false, timestamp, timestamp);
		lecturerRepository.save(newLecturer);
		return 0;
	}

	/**
	 * 講師指導科目設定（新規・更新）： TODO: 初回と2回目以降でHTTPメソッドを変える必要あり（POST,PUT）
	 */
	@Transactional
	public void decideLecturerTeachSubjects() {
		Lecturer lecturer = new Lecturer(1);
		Subject subject = new Subject("m3");
		Classroom classroom = new Classroom(1);
		// 作成日時、更新日時は同じ
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		LecturerTeachSubject lecturerTeachSubject = new LecturerTeachSubject(lecturer, subject, classroom, true,
				"comment", timestamp, timestamp);
		lecturerTeachSubjectRepository.save(lecturerTeachSubject);
	}

	/**
	 * 講師定期コマ送信： TODO: 初回と2回目以降でHTTPメソッドを変える必要あり（POST,PUT）
	 */
	@Transactional
	public void decideLecturerWorkableTimeNormal() throws Exception {
		List<LecturerWorkableTime> lecturerWorkableTime = new ArrayList<>();
		final int TIME_TABLE_COUNT = 18;
		Classroom targetclassroom = new Classroom(1);
		Lecturer lecturer = new Lecturer(1);
		for (int timeTableId = 1; timeTableId <= TIME_TABLE_COUNT; timeTableId++) {
			TimeTableNormal timeTableNormal = new TimeTableNormal(timeTableId);
			lecturerWorkableTime.add(new LecturerWorkableTime(targetclassroom, lecturer, timeTableNormal, false));
		}
		lecturerWorkableTimeRepository.saveAll(lecturerWorkableTime);
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

			// テーブルに保存
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
