package com.eno.tkg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.LecturerWorkableTime;
import com.eno.tkg.entity.master.Employee;

@Repository
public interface LecturerWorkableTimeRepository extends CrudRepository<LecturerWorkableTime, Integer> {

}
