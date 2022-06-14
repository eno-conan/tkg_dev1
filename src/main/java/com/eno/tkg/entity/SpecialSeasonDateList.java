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
import javax.persistence.FetchType;
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
@Table(name = "special_season_date_list")
@NoArgsConstructor
@Data
public class SpecialSeasonDateList implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "special_season_id", nullable = false)
	@JsonIgnore
	private SpecialSeason specialSeason;

	@Column(name = "class_date")
	@Temporal(TemporalType.DATE)
	private Date classDate;
	
	@OneToMany(mappedBy = "specialSeasonDateList", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<TimeTableSpecial> timeTableSpecial;
	
}
