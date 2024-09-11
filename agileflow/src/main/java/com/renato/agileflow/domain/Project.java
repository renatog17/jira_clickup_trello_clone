package com.renato.agileflow.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.controllers.dto.UpdateProjectDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime createdAt;
	@OneToMany(mappedBy = "project")
	private List<Board> boards;
	private boolean excluded;

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.excluded = false;
	}

	public Project(@Valid CreateProjectDTO projectDTO) {
		this.name = projectDTO.name();
		this.description = projectDTO.description();
		this.startDate = projectDTO.startDate();
		this.endDate = projectDTO.endDate();
		this.createdAt = LocalDateTime.now();
		this.excluded = false;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public boolean isExcluded() {
		return excluded;
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
		Project other = (Project) obj;
		return Objects.equals(id, other.id);
	}

	public void logicallyDelete() {
		this.excluded = true;

	}

	@Transactional
	public void update(UpdateProjectDTO updateProjectDTO) {
		this.description = updateProjectDTO.description();

	}

}
