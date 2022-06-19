package com.eno.tkg.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	
	public List<Subject> findAll();
	
//	public Optional<List<Subject>> findBy 

}
