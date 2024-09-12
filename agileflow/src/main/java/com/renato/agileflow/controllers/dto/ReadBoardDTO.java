package com.renato.agileflow.controllers.dto;

import com.renato.agileflow.domain.Board;

public record ReadBoardDTO(
		Long project_id,
		String name,
		String description) {

	public ReadBoardDTO(Board board) {
		this(board.getProject().getId(), board.getName(), board.getDescription());
	}

}
