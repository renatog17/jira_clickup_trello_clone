package com.renato.agileflow.controllers.dto;

import java.time.LocalDate;

import com.renato.agileflow.domain.Project;

public record ReadProjectDTO(
		Long id,
		String name,
		String descricao,
		LocalDate startDate,
		LocalDate endDate){

	public ReadProjectDTO(Project project) {
		this(
				project.getId(),
				project.getName(), 
				project.getDescription(), 
				project.getStartDate(),
				project.getEndDate());
	}
}
