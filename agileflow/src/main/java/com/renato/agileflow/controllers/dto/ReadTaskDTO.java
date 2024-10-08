package com.renato.agileflow.controllers.dto;

import java.time.LocalDateTime;

import com.renato.agileflow.domain.Task;

public record ReadTaskDTO(String title,
		String description,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime dueDate) {
	
	public ReadTaskDTO(Task task) {
		this(task.getTitle(), task.getDescription(), task.getCreatedAt(), task.getUpdatedAt(), task.getDueDate());
	}

}
