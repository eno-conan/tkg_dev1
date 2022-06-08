package com.eno.tkg.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.Member;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Grade;

@Repository
public interface StudentScheduleNormalRepository extends CrudRepository<StudentScheduleNormal, Integer> {

	public List<StudentScheduleNormal> findAllByClassDate(Date date);

}
