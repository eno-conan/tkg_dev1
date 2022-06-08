package com.eno.tkg.entity.master;

import java.sql.Timestamp;
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
import javax.persistence.Table;

import com.eno.tkg.entity.LecturerTeachSubject;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_subject")
@NoArgsConstructor
@Data
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String subjectKey;

	@Column(name = "subject_division", length = 32, nullable = false)
	private String subjectDivision;

	@Column(name = "display_name", length = 32, nullable = false)
	private String displayName;

	@Column(name = "delete_flg", length = 2)
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<StudentSubject> studentSubjects;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<StudentScheduleNormal> studentScheduleNormal;

	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<LecturerTeachSubject> lecturerTeachSubject;

	public Subject(String subjectKey) {
		this.subjectKey = subjectKey;
	}

}
