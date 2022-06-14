package com.eno.tkg.entity.master;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eno.tkg.entity.LecturerWorkableTime;
import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.StudentSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "time_table_special")
@Data
@NoArgsConstructor
public class TimeTableSpecial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "special_season_date_id", nullable = false)
	@JsonIgnore
	private SpecialSeasonDateList specialSeasonDateList;

	@Column(name = "period", length = 128, nullable = false)
	private String period;
	
	@OneToMany(mappedBy = "timeTableSpecial", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<StudentScheduleSpecial> studentScheduleSpecial;
	
//	@OneToMany(mappedBy = "TimeTableSpecial", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<StudentSubject> studentSubjects;
//	
//	@OneToMany(mappedBy = "TimeTableSpecial", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<LecturerWorkableTime> lecturerWorkableTime;

	public TimeTableSpecial(Integer id) {
		this.id = id;
	}

}
