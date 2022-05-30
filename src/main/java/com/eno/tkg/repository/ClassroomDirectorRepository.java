package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.ClassroomDirector;


@Repository
public interface ClassroomDirectorRepository extends CrudRepository<ClassroomDirector, Integer>{

}
