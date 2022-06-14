package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.Member;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;

@Repository
public interface StudentScheduleSpecialRepository extends JpaRepository<StudentScheduleSpecial, Integer> {

	public List<StudentScheduleSpecial> findAll();

	public List<Optional<StudentScheduleSpecial>> findByStudentOrderByClassDate(Student student);

	Optional<StudentScheduleSpecial> findByStudentAndTimeTableSpecial(Student student,
			TimeTableSpecial timeTableSpecial);

}
