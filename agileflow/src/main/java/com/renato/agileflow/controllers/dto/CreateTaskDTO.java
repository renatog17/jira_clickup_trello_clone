package com.renato.agileflow.controllers.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record CreateTaskDTO(
		@NotNull(message = "the title cannot be null")
		String title,
		String description,
		LocalDateTime dueDate,
		Long board
		) {

}
