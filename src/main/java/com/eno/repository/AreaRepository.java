package com.eno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.Member;
import com.eno.entity.master.Area;
import com.eno.entity.master.Grade;

@Repository
public interface AreaRepository extends CrudRepository<Area, Integer>{
	
	public List<Area> findAll();

}