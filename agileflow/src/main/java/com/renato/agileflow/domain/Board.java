package com.renato.agileflow.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.renato.agileflow.controllers.dto.CreateBoardDTO;
import com.renato.agileflow.controllers.dto.UpdateBoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	private String name;
	private String description;
	@Column(updatable = false)
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private String label;
	private String priority;
	private String backgroundColor;
	private Boolean excluded;
	@OneToMany(mappedBy = "board")
	private List<Task> tasks = new ArrayList<>();
	
	
	public Boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private Usuario createdBy;
	

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
		this.excluded = false;
	}

	public Board(CreateBoardDTO createBoardDTO) {
		this.backgroundColor = createBoardDTO.backgroundColor();
		this.description = createBoardDTO.description();
		this.label = createBoardDTO.label();
		this.name = createBoardDTO.name();
		this.project = new Project(createBoardDTO.project());
		this.createdAt = LocalDateTime.now();
	}
	

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Board(Long board) {
		this.id = board;
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

	
	public List<Task> getTasks() {
		return tasks;
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

	public void update(UpdateBoardDTO updateBoardDTO) {
		this.name = updateBoardDTO.name();
		this.description = updateBoardDTO.description();
		this.label = updateBoardDTO.label();
		this.priority = updateBoardDTO.priority();
		this.backgroundColor = updateBoardDTO.backgroundColor();
		
	}

	public void logicallyDelete() {
		this.excluded = true;
	}

}
