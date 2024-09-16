package com.renato.agileflow.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.agileflow.controllers.dto.CreateBoardDTO;
import com.renato.agileflow.controllers.dto.ReadBoardDTO;
import com.renato.agileflow.domain.Board;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.repositories.BoardRepository;
import com.renato.agileflow.repositories.ProjectRepository;

@RequestMapping("/board")
@RestController
public class BoardController {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired 
	private BoardRepository boardRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postBoard(@RequestBody CreateBoardDTO createBoardDTO, UriComponentsBuilder uriComponentsBuilder){
		Optional<Project> optionalProject = projectRepository.findById(createBoardDTO.project());
		if(optionalProject.isEmpty())
			return ResponseEntity.notFound().build();
		Board board = new Board(createBoardDTO);
		boardRepository.save(board);
		URI uri = uriComponentsBuilder.path("/board/{id}").buildAndExpand(board.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBoard(@PathVariable Long id){
		Optional<Board> optionalBoard = boardRepository.findById(id);
		if(optionalBoard.isEmpty())
			return ResponseEntity.notFound().build();
		ReadBoardDTO readBoardDTO = new ReadBoardDTO(optionalBoard.get());
		return ResponseEntity.ok(readBoardDTO);
	}
}