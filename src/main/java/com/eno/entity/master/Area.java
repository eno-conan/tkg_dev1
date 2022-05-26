package com.eno.entity.master;

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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_area")
@Data
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="area_name",length = 128, nullable = false, unique = true)
	private String areaName;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	@OneToMany(mappedBy = "mArea", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Prefecture> prefectures = new ArrayList<>();

}
