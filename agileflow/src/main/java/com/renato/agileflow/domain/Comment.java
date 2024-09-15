package com.renato.agileflow.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	private LocalDateTime createAt;
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(String content, LocalDateTime createAt) {
		super();
		this.content = content;
		this.createAt = createAt;
	}

	
}
