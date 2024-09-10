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

import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.controllers.dto.ReadProjectDTO;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.repositories.ProjectRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postProject(@RequestBody @Valid CreateProjectDTO projectDTO, UriComponentsBuilder uriComponentsBuilder){
		Project project = new Project(projectDTO);
		projectRepository.save(project);
		URI uri = uriComponentsBuilder.path("/project/{id}").buildAndExpand(project.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProject(@PathVariable Long id){
		Optional<Project> optionalProject = projectRepository.findById(id);
		if(optionalProject.isPresent()) {
			return ResponseEntity.ok(new ReadProjectDTO(optionalProject.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
