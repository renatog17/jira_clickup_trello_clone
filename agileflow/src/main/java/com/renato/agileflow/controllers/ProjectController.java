package com.renato.agileflow.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
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

import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.controllers.dto.ReadProjectDTO;
import com.renato.agileflow.controllers.dto.ReadProjectWithBoardsDTO;
import com.renato.agileflow.controllers.dto.UpdateProjectDTO;
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
	public ResponseEntity<?> postProject(@RequestBody @Valid CreateProjectDTO projectDTO,
			UriComponentsBuilder uriComponentsBuilder) {
		Project project = new Project(projectDTO);
		projectRepository.save(project);
		URI uri = uriComponentsBuilder.path("/project/{id}").buildAndExpand(project.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProject(@PathVariable Long id) {
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {
			return ResponseEntity.ok(new ReadProjectDTO(optionalProject.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
	public Page<ReadProjectDTO> getAllProjects(@PageableDefault(size = 10) Pageable pageable) {
		return projectRepository.findAllByExcludedFalse(pageable).map(ReadProjectDTO::new);
	}

	@GetMapping("/{id}/boards")
	public ResponseEntity<?> getProjectWithBoards(@PathVariable Long id){
		Optional<Project> optionalProject = projectRepository.findById(id);
		if(optionalProject.isEmpty())
			return ResponseEntity.notFound().build();
		Project projeto = optionalProject.get();
		ReadProjectWithBoardsDTO readProjectWithBoardsDTO = new ReadProjectWithBoardsDTO(projeto);
		return ResponseEntity.ok(readProjectWithBoardsDTO);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteProject(@PathVariable Long id) {
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {

			Project referenceById = projectRepository.getReferenceById(id);
			if (referenceById.isExcluded()) {
				return ResponseEntity.notFound().build();
			}
			if(referenceById.getBoards().size()>0) {
				return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
			}
			referenceById.logicallyDelete();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//necessita ser testado
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody UpdateProjectDTO updateProjectDTO){
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {
			Project project = optionalProject.get();
			if(!project.isExcluded()) {
				project.update(updateProjectDTO);
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
}
