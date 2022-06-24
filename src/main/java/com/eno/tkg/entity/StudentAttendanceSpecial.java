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
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_attendance_special")
@NoArgsConstructor
@Data
public class StudentAttendanceSpecial implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "special_season_id", nullable = true)
	@JsonIgnore
	private SpecialSeason specialSeason;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonIgnore
	private Student student;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "time_table_special_id", nullable = true)
	@JsonIgnore
	private TimeTableSpecial timeTableSpecial;
	

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Transient
	private String receiveErrorMessage;

	@Override
	public StudentAttendanceSpecial clone() {
		StudentAttendanceSpecial ssn = new StudentAttendanceSpecial();
		ssn.id = id;
		ssn.student = student;
		ssn.timeTableSpecial = timeTableSpecial;
		ssn.createdAt = createdAt;
		ssn.updatedAt = updatedAt;
		ssn.receiveErrorMessage = receiveErrorMessage;
		return ssn;
	}

	public StudentAttendanceSpecial(String message) {
		this.receiveErrorMessage = message;
	}

	public StudentAttendanceSpecial(Date classDate, TimeTableSpecial timeTableSpecial) {
		this.timeTableSpecial = timeTableSpecial;
	}

}
