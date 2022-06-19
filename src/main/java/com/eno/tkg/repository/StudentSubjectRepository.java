package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentSubject;
import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableNormal;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer>{
		
	public List<StudentSubject> findByTimeTableNormalIn(List<TimeTableNormal> timeTableNormalIds);
	
	public List<StudentSubject> findByTimeTableNormal(final TimeTableNormal timeTableNormalId);
	
	public List<StudentSubject> findByStudent(final Student student);

}
