package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.SubjectTargetGrade;


@Repository
public interface SubjectTargetGradeRepository extends JpaRepository<SubjectTargetGrade, Integer>{
	
	Optional<List<SubjectTargetGrade>> findByGrade(Grade grade);

}
