package com.eno.tkg.entity.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_grade")
@Data
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String gradeKey;

	@Column(name = "grade_division", length = 32, nullable = false)
	private String gradeDivision;

	@Column(name = "display_name", length = 32, nullable = false)
	private String displayName;
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Student> students;

}
