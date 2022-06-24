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

import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_student")
@NoArgsConstructor
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

	@Column(name = "name", length = 128, nullable = false, unique = true)
	private String studentName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "delete_flg", length = 2)
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	private List<StudentSubject> studentSubjects;

	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<StudentScheduleNormal> studentScheduleNormal;

	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<StudentScheduleSpecial> studentScheduleSpecial;

	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<StudentClassSpecialSummary> studentClassSpecialSummary;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<StudentAttendanceSpecial> studentAttendanceSpecial;


	public Student(Integer id) {
		this.id = id;
	}

//	public Student insertStudent() {
//		Student initState = new Student();
//		initState.setDeleteFlg(false);
//		initState.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//		initState.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//		return null;
//	}

}
