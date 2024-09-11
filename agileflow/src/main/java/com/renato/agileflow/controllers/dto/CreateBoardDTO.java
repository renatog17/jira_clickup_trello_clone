package com.renato.agileflow.controllers.dto;



public record CreateBoardDTO(

		Long project,
		String name,
		String description,
		String label,
		String priority,
		String backgroundColor) {

}
