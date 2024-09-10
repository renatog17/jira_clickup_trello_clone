package com.renato.agileflow.controllers.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record CreateProjectDTO(
		@NotNull(message = "tha name cannot be null")
		String name,
		@NotNull(message = "the description cannot be null")
		String description,
		@NotNull(message = "the start date cannot be null")
		@PastOrPresent
		LocalDate startDate,
		LocalDate endDate) {

}
