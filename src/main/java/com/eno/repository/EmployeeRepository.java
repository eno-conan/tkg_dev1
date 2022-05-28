package com.eno.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.master.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
