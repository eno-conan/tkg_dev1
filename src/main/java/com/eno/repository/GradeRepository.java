package com.eno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.Member;
import com.eno.entity.master.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>{
	
	public List<Grade> findAll();

}
