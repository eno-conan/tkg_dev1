package com.eno.tkg.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Prefecture;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_schedule_normal")
@NoArgsConstructor
@Data
public class StudentScheduleNormal {

	public StudentScheduleNormal(String message) {
		this.receiveErrorMessage = message;
	}

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
	@JoinColumn(name = "lecturer_id")
	@JsonIgnore
	private Lecturer lecturer;

	@Column(name = "period", nullable = false)
	private String period;

	@Column(name = "class_date")
	@Temporal(TemporalType.DATE)
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

	@Transient
	private String receiveErrorMessage;

}
