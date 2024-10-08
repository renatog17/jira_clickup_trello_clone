package com.renato.agileflow.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.agileflow.controllers.dto.CreateBoardDTO;
import com.renato.agileflow.controllers.dto.ReadBoardDTO;
import com.renato.agileflow.controllers.dto.ReadBoardWithTasksDTO;
import com.renato.agileflow.controllers.dto.UpdateBoardDTO;
import com.renato.agileflow.domain.Board;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.domain.Usuario;
import com.renato.agileflow.repositories.BoardRepository;
import com.renato.agileflow.repositories.ProjectRepository;
import com.renato.agileflow.services.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequestMapping("/board")
@RestController
@SecurityRequirement(name = "bearer-key")
public class BoardController {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired 
	private BoardRepository boardRepository;
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postBoard(@RequestBody CreateBoardDTO createBoardDTO, UriComponentsBuilder uriComponentsBuilder){
		Optional<Project> optionalProject = projectRepository.findById(createBoardDTO.project());
		if(optionalProject.isEmpty())
			return ResponseEntity.notFound().build();
		Usuario usuario = usuarioService.obterUsuarioAutenticado();
		Board board = new Board(createBoardDTO);
		board.setCreatedBy(usuario);
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
	
	@GetMapping
	public Page<ReadBoardDTO> getAllBoards(@PageableDefault(size = 10) Pageable pageable){
		return boardRepository.findAllByExcludedFalse(pageable).map(ReadBoardDTO::new);
	}
	
	@GetMapping("/{id}/tasks")
	public ResponseEntity<?> getBoardsWithTasks(@PathVariable Long id) {
		Optional<Board> optionalBoard = boardRepository.findById(id);
		if(optionalBoard.isEmpty())
			return ResponseEntity.notFound().build();
		Board board = optionalBoard.get();
		ReadBoardWithTasksDTO readProjectWithBoardsDTO = new ReadBoardWithTasksDTO(board);
		return ResponseEntity.ok(readProjectWithBoardsDTO);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> putBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO updateBoardDTO){
		Optional<Board> optionalBoard = boardRepository.findById(id);
		if(optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			if(!board.isExcluded()) {
				board.update(updateBoardDTO);
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}") 
	@Transactional
	public ResponseEntity<?> deleteBoad(@PathVariable Long id){
		Optional<Board> optionalBoard = boardRepository.findByIdAndExcludedFalseAndTasksEmpty(id);
		if (optionalBoard.isPresent()) {
			optionalBoard.get().logicallyDelete();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
