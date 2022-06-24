package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.LecturerTeachSubject;
import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;

@Repository
public interface StudentAttendanceSpecialRepository extends CrudRepository<StudentAttendanceSpecial, Integer>{
	
	List<StudentAttendanceSpecial> findBySpecialSeasonAndStudent(SpecialSeason specialSeason,Student student);
	
	void deleteBySpecialSeasonAndStudent(long id, long userId);
	
}
