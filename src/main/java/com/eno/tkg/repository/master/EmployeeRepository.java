package com.eno.tkg.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
