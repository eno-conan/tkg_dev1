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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eno.tkg.entity.ClassroomDirector;
import com.eno.tkg.entity.LecturerTeachSubject;
import com.eno.tkg.entity.LecturerWorkableTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_classroom")
@NoArgsConstructor
@Data
public class Classroom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "prefecture_id")
	@JsonIgnore
	private Prefecture mPrefecture;// variable name write mappedBy's value

	@Column(name = "classroom_name", length = 128, nullable = false, unique = true)
	private String classroomName;

	@Column(name = "address", length = 256, nullable = false)
	private String address;

	@Column(name = "delete_flg", length = 2)
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToOne(mappedBy = "classroom")
	private ClassroomDirector classroomDirector;

	// 大きな教室の場合、複数人になるため
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<Lecturer> employee;

	@OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<Lecturer> lecturers;

	@OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<Student> students;

	public Classroom(Integer id) {
		this.id = id;
	}

	public Classroom(Prefecture mPrefecture, String classroomName, String address, boolean deleteFlg,
			Timestamp createdAt, Timestamp updatedAt) {
		this.mPrefecture = mPrefecture;
		this.classroomName = classroomName;
		this.address = address;
		this.deleteFlg = deleteFlg;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
