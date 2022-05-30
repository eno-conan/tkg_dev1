package com.eno.tkg.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "articles")
@Data
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(length = 64, nullable = false, unique = true)
	private String title;
	
	@Column(length = 1024, nullable = false)
	private String description;
	
	@Column(length = 2, nullable = false)
	private boolean deleteFlg;

}
