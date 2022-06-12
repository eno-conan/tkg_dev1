package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;

@Repository
public interface StudentClassSpecialSummaryRepository extends JpaRepository<StudentClassSpecialSummary, Integer> {

	public List<StudentClassSpecialSummary> findBySpecialSeasonAndStudent(SpecialSeason specialSeason, Student student);

}
