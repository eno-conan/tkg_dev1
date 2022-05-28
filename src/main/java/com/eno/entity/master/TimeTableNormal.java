package com.eno.entity.master;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eno.entity.LecturerWorkableTime;
import com.eno.entity.StudentSubject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "time_table_normal")
@Data
@NoArgsConstructor
public class TimeTableNormal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="day_of_week",length = 128, nullable = false)
	private String dayOfWeek;
	
	@Column(name="period",length = 128, nullable = false)
	private String period;

	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentSubject> studentSubjects;
	
	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LecturerWorkableTime> lecturerWorkableTime;

	public TimeTableNormal(Integer id) {
		this.id = id;
	}
	
}
