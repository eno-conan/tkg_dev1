package com.eno.tkg.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer>{
	
//	public List<Optional<StudentScheduleSpecial>> findAll();

}
