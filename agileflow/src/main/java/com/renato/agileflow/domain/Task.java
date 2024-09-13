package com.renato.agileflow.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime dueDate;
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

//	private List<Label> labels;       // Lista de labels associadas à tarefa
//    private List<Comment> comments;   // Lista de comentários na tarefa
//    private List<Attachment> attachments; // Lista de anexos relacionados à tarefa
//    private List<Checklist> checklists;
	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt,
			LocalDateTime dueDate, Board board) {
		super();
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.dueDate = dueDate;
		this.board = board;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public Board getBoard() {
		return board;
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
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", dueDate=" + dueDate + ", board=" + board + "]";
	}

}
