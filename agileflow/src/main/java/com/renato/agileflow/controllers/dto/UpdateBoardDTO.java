package com.renato.agileflow.controllers.dto;

public record UpdateBoardDTO(
		String name,
		String description,
		String label,
		String priority,
		String backgroundColor) {

}
