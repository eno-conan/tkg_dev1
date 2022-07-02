package com.eno.tkg.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableNormal;

@Repository
public interface StudentScheduleNormalRepository extends JpaRepository<StudentScheduleNormal, Integer> {

	// 対象日付に実施予定の全授業取得
	public List<StudentScheduleNormal> findAllByClassDateOrderByTimeTableNormalAsc(Date date);

	// あるコマの講師の授業を取得
	public List<StudentScheduleNormal> findAllByLecturerAndClassDateAndTimeTableNormal(Lecturer lecturer, Date date,
			TimeTableNormal timeTableNormal);

	// あるコマの生徒の授業予定を取得
	public Optional<StudentScheduleNormal> findByStudentAndClassDateAndTimeTableNormal(Student student, Date date,
			TimeTableNormal timeTableNormal);

	// 生徒の授業予定を取得
	public List<StudentScheduleNormal> findByStudentAndClassDateAfterOrderByClassDateAsc(Student student, Date date);

}
