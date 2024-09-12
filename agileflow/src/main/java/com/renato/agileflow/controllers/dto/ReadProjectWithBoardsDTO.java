package com.renato.agileflow.controllers.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.renato.agileflow.domain.Project;

public record ReadProjectWithBoardsDTO(Long id,
		String name,
		String descricao,
		LocalDate startDate,
		LocalDate endDate,
		List<ReadBoardDTO> boards){
	
	public ReadProjectWithBoardsDTO(Project project){
		this(project.getId(), 
				project.getName(), 
				project.getDescription(), 
				project.getStartDate(), 
				project.getEndDate(), 
				 project.getBoards().stream()
                 .map(board -> new ReadBoardDTO(board))
                 .collect(Collectors.toList()));
	}
}
