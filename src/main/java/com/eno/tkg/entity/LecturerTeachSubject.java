package com.eno.tkg.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableNormal;

import lombok.Data;

@Entity
@Table(name = "lecturer_teach_subject")
@Data
public class LecturerTeachSubject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	private Lecturer lecturer;

	@ManyToOne
	@JoinColumn(name = "subject_key")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "classroom_id", nullable = false)
	private Classroom classroom;

	@Column(name = "teach_flg", length = 2)
	private boolean teachFlg;

	@Column(name = "comment", length = 64)
	private String comment;
	
	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	public LecturerTeachSubject(Lecturer lecturer, Subject subject, Classroom classroom, boolean teachFlg,
			String comment, Timestamp createdAt, Timestamp updatedAt) {
		this.lecturer = lecturer;
		this.subject = subject;
		this.classroom = classroom;
		this.teachFlg = teachFlg;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	

}
