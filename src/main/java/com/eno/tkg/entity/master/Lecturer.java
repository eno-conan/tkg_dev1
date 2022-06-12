package com.eno.tkg.entity.master;

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

import com.eno.tkg.entity.LecturerTeachSubject;
import com.eno.tkg.entity.LecturerWorkableTime;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_lecturer")
@NoArgsConstructor
@Data
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;

	@Column(name = "name", length = 128, nullable = false, unique = true)
	private String lecturerName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "delete_flg")
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentSubject> studentSubjects;

	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentScheduleNormal> studentScheduleNormal;
	
	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LecturerTeachSubject> lecturerTeachSubject;
	
	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LecturerWorkableTime> lecturerWorkableTime;
	
	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentScheduleSpecial> studentScheduleSpecial;


	public Lecturer(Integer id) {
		this.id = id;
	}
	
	
	public Lecturer(Classroom classroom, String lecturerName, Date birthday, boolean deleteFlg, Timestamp createdAt,
			Timestamp updatedAt) {
		this.classroom = classroom;
		this.lecturerName = lecturerName;
		this.birthday = birthday;
		this.deleteFlg = deleteFlg;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
