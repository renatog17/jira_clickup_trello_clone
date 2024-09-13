package com.renato.agileflow.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.repositories.ProjectRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProjectRepository projectRepository;
	
	@Test
	@DisplayName("Deve criar um novo projeto")
	public void postProjectTest() throws Exception {
		CreateProjectDTO createProjectDTO = new CreateProjectDTO("Projeto teste", "descrição teste", LocalDate.of(2024, 8, 12), null);
		when(projectRepository.save(any(Project.class))).thenReturn(new Project(createProjectDTO));
		
		  mockMvc.perform(post("/project")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content("{\"name\": \"Projeto teste\", \"description\": \"descrição teste\", \"startDate\": \"2024-08-12\", \"endDate\": null}"))
		            .andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Deve retornar erro 400 ao tentar criar um novo projeto")
	public void postProjectTest400() throws Exception {
		CreateProjectDTO createProjectDTO = new CreateProjectDTO("Projeto teste", "descrição teste", LocalDate.of(2024, 8, 12), null);
		when(projectRepository.save(any(Project.class))).thenReturn(new Project(createProjectDTO));
		
		  mockMvc.perform(post("/project")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content("{\"name\": \"\", \"description\": \"descrição teste\", \"startDate\": \"2024-08-12\", \"endDate\": null}"))
		            .andExpect(status().isCreated());
	}
	
	
}
