package com.eno.tkg.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>{
	
	public List<Grade> findAll();

}
