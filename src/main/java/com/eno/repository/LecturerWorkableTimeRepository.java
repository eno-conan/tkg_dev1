package com.eno.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.LecturerWorkableTime;
import com.eno.entity.master.Employee;

@Repository
public interface LecturerWorkableTimeRepository extends CrudRepository<LecturerWorkableTime, Integer> {

}
