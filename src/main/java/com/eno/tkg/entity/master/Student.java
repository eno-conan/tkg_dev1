package com.eno.tkg.entity.master;

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

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "m_student")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;
	
	@ManyToOne
	@JoinColumn(name = "grade_key")
	@JsonIgnore
	private Grade grade;

	@Column(name="name",length = 128, nullable = false, unique = true)
	private String studentName;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="delete_flg",length = 2)
	private boolean deleteFlg;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentSubject> studentSubjects;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentScheduleNormal> studentScheduleNormal;

}
