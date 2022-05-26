package com.eno.entity.master;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eno.entity.StudentScheduleNormal;
import com.eno.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "m_lecturer")
@Data
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;

	@Column(name="name",length = 128, nullable = false, unique = true)
	private String lecturerName;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="delete_flg")
	private boolean deleteFlg;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentSubject> studentSubjects;
	
	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentScheduleNormal> studentScheduleNormal;

}
