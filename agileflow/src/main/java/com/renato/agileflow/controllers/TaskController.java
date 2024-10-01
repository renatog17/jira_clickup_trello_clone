package com.renato.agileflow.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.agileflow.controllers.dto.CreateTaskDTO;
import com.renato.agileflow.domain.Task;
import com.renato.agileflow.repositories.TaskRepository;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskRepository taskRepository;
	
	//agora pra testar preciso que haja um projeto e um board cadastrado no banco
	//usar teste no spring mesmo, sem usar o postman
	@PostMapping()
	public ResponseEntity<?> postTask(@RequestBody CreateTaskDTO createTaskDto, UriComponentsBuilder uriComponentsBuilder){
		Task task = new Task(createTaskDto);
		taskRepository.save(task);
		URI uri = uriComponentsBuilder.path("/task/{id}").buildAndExpand(task.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<?> getTask(){
		return null;
	}
	
	@PutMapping
	public ResponseEntity<?> putTask(){
		return null;
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTask(){
		return null;
	}
}
