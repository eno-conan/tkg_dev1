package com.eno.tkg.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;

@Repository
public interface StudentScheduleSpecialRepository extends JpaRepository<StudentScheduleSpecial, Integer> {

	public List<StudentScheduleSpecial> findAll();

	public List<Optional<StudentScheduleSpecial>> findByStudentOrderByClassDate(Student student);

	// 現在より未来の講習会情報を取得
	public List<StudentScheduleSpecial> findByStudentAndClassDateAfterOrderByClassDateAsc(Student student, Date date);

	Optional<StudentScheduleSpecial> findByStudentAndTimeTableSpecial(Student student,
			TimeTableSpecial timeTableSpecial);

}
