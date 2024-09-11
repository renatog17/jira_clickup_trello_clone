package com.renato.agileflow.controllers.dto;

import com.renato.agileflow.domain.Board;

public record ReadBoardDTO(
		String name,
		String description) {

	public ReadBoardDTO(Board board) {
		this(board.getName(), board.getDescription());
	}

}
