package com.eno.tkg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.eno.tkg.entity.master.Classroom;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.TimeTableNormal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecturer_workable_time")
@Data
@NoArgsConstructor
public class LecturerWorkableTime implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	private Lecturer lecturer;

	@ManyToOne
	@JoinColumn(name = "time_table_normal_id", nullable = false)
	private TimeTableNormal timeTableNormal;

	@Column(name = "workable_flg", length = 2)
	private boolean workableFlg;

	public LecturerWorkableTime(Lecturer lecturer, TimeTableNormal timeTableNormal,
			boolean workableFlg) {
		this.lecturer = lecturer;
		this.timeTableNormal = timeTableNormal;
		this.workableFlg = workableFlg;
	}

}
