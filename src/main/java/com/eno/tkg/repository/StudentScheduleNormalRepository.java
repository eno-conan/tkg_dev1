package com.eno.tkg.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.Member;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;

@Repository
public interface StudentScheduleNormalRepository extends JpaRepository<StudentScheduleNormal, Integer> {

	public List<StudentScheduleNormal> findAllByClassDateOrderByPeriod(Date date);

	// あるコマの講師の授業を取得
	public List<StudentScheduleNormal> findAllByLecturerAndClassDateAndPeriod(Lecturer lecturer, Date date,
			String period);

	// あるコマの生徒の授業予定を取得
	public Optional<StudentScheduleNormal> findByStudentAndClassDateAndPeriod(Student student, Date date,
			String period);

	// 生徒の授業予定を取得
	public List<StudentScheduleNormal> findByStudentAndClassDateAfterOrderByClassDateAsc(Student student, Date date);

}
