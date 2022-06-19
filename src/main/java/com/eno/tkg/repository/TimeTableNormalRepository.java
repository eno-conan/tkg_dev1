package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.entity.master.TimeTableSpecial;

@Repository
public interface TimeTableNormalRepository extends JpaRepository<TimeTableNormal, Integer>{ 

	@Query(value = 
			"select * from time_table_normal as ttn "
			+"where not ttn.id in "
			+"( "
			+"select stu_subj.time_table_normal_id from student_subject as stu_subj "
			+"where stu_subj.student_id= :studentId "
			+") ",
			  nativeQuery = true)
public Optional<List<TimeTableNormal>> getFramesTargetStudentNoClass(@Param("studentId") String studentId);

}
