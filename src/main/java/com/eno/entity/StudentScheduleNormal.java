package com.eno.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eno.entity.master.Lecturer;
import com.eno.entity.master.Prefecture;
import com.eno.entity.master.Student;
import com.eno.entity.master.Subject;
import com.eno.entity.master.TimeTableNormal;

import lombok.Data;

@Entity
@Table(name = "student_schedule_normal")
@Data
public class StudentScheduleNormal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_key")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	private Lecturer lecturer;

	@Column(name = "class_date")
	private Date classDate;
	
	@Column(name = "reschedule_date_start")
	private Date rescheduleDateStart;
	
	@Column(name = "reschedule_date_last")
	private Date rescheduleDateLast;
	
	@Column(name = "reschedule_flg")
	private boolean rescheduleFlg;
	
	@Column(name = "status")
	private int status;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

}