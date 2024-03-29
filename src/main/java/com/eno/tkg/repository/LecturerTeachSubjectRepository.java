package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.LecturerTeachSubject;
import com.eno.tkg.entity.master.Grade;

@Repository
public interface LecturerTeachSubjectRepository extends JpaRepository<LecturerTeachSubject, Integer>{
	
}
