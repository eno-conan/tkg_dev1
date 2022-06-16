package com.eno.tkg.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	public List<Student> findAll();

}
