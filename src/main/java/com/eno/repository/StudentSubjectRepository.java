package com.eno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.StudentSubject;
import com.eno.entity.master.Classroom;
import com.eno.entity.master.Student;
import com.eno.entity.master.TimeTableNormal;

@Repository
public interface StudentSubjectRepository extends CrudRepository<StudentSubject, Integer>{
		
	public List<StudentSubject> findByTimeTableNormalIn(List<TimeTableNormal> timeTableNormalIds);
	
	public List<StudentSubject> findByTimeTableNormal(final TimeTableNormal timeTableNormalId);
	
	public List<StudentSubject> findByStudent(final Student student);

}
