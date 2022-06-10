package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Classroom;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, Integer>{
	
	public List<Classroom> findAll();

}