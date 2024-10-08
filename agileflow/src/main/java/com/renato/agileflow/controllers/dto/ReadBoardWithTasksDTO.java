package com.renato.agileflow.controllers.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.renato.agileflow.domain.Board;

public record ReadBoardWithTasksDTO(
		
		Long project_id,
		String name,
		String description,
		List<ReadTaskDTO> tasks) {
	
	public ReadBoardWithTasksDTO(Board board) {
		this(board.getId(), 
				board.getName(), 
				board.getDescription(), 
				board.getTasks().stream()
				.map(task -> new ReadTaskDTO(task))
				.collect(Collectors.toList()));
		}

}
