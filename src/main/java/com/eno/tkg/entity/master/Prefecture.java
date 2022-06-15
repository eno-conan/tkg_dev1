package com.eno.tkg.entity.master;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "m_prefecture")
@Data
public class Prefecture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "area_id")
	@JsonIgnore
	private Area mArea;//variable name write mappedBy's value

	@Column(name="prefecture_name",length = 128, nullable = false, unique = true)
	private String prefectureName;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "mPrefecture", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Classroom> classrooms;

}
