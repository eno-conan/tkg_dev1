package com.eno.tkg.entity.master;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eno.tkg.entity.LecturerWorkableTime;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentSubject;

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

	// 要らないかも
	@Column(name = "day_of_week", length = 16, nullable = false)
	private String dayOfWeek;

	@Column(name = "period", length = 16, nullable = false)
	private String period;

	@Column(name = "day_of_week_ja", length = 16, nullable = false)
	private String dayOfWeekJa;

	@OneToMany(mappedBy = "timeTableNormal", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = false)
	private List<StudentSubject> studentSubjects;

	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<LecturerWorkableTime> lecturerWorkableTime;

	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = false)
	private List<StudentScheduleNormal> studentScheduleNormal;

	public TimeTableNormal(Integer id) {
		this.id = id;
	}

}
