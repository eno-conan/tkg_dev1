package com.eno.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.ClassroomDirector;


@Repository
public interface ClassroomDirectorRepository extends CrudRepository<ClassroomDirector, Integer>{

}
