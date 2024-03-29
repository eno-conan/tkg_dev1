package com.eno.tkg.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_subject")
@NoArgsConstructor
@Data
public class StudentSubject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonIgnore
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_key")
	@JsonIgnore
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "assigned_lecturer_id")
	@JsonIgnore
	private Lecturer lecturer;

	@ManyToOne
	@JoinColumn(name = "time_table_normal_id", nullable = false)
	@JsonIgnore
	private TimeTableNormal timeTableNormal;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Transient
	private String receiveErrorMessage;

	public StudentSubject(String receiveErrorMessage) {
		this.receiveErrorMessage = receiveErrorMessage;
	}

}
