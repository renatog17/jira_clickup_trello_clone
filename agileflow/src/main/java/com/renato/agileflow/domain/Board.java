package com.renato.agileflow.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	private String name;
	private String description;
	@Column(updatable = false)
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private String label;
	private String priority;
	private String backgroundColor;

	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board(Project project, String name, String description, LocalDateTime updateAt, String label,
			String priority, String backgroundColor) {
		super();
		this.project = project;
		this.name = name;
		this.description = description;
		this.updateAt = updateAt;
		this.label = label;
		this.priority = priority;
		this.backgroundColor = backgroundColor;
	}

	public Long getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public String getLabel() {
		return label;
	}

	public String getPriority() {
		return priority;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		return Objects.equals(id, other.id);
	}

}
