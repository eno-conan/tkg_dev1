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

import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "special_season")
@NoArgsConstructor
@Data
public class SpecialSeason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="year",length = 16, nullable = false)
	private String studentName;
	
	@Column(name="season_name",length = 16, nullable = false)
	private String seasonName;
	
	@OneToMany(mappedBy = "specialSeason", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentClassSpecialSummary> studentClassSpecialSummary;
	
	@OneToMany(mappedBy = "specialSeason", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SpecialSeasonDateList> studentSeasonDateList;
	
	public SpecialSeason(Integer id) {
		this.id = id;
	}

}
