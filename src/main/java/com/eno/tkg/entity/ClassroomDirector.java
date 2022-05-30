package com.eno.tkg.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "classroom_director")
@Data
public class ClassroomDirector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;
	
	@OneToOne
	@JoinColumn(name="employee_id")
	@JsonIgnore
	private Employee employee;
	
	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	public ClassroomDirector(Classroom classroom, Employee employee, Timestamp createdAt, Timestamp updatedAt) {
		this.classroom = classroom;
		this.employee = employee;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
}
