package com.renato.agileflow.controllers;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.repositories.ProjectRepository;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ProjectControllerTest {
//O banco não está sendo meio que resetado, ou seja, o id não começa do zero e eu ainda estou 
//	pensando em uma maneira de fazer isso
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@AfterEach
	public void cleanDatabase() {
		projectRepository.deleteAll();
	}
	
	@Test
	public void testPostProject() throws Exception {
		// Arrange
		CreateProjectDTO projectDTO = new CreateProjectDTO("New Project", "Project description", LocalDate.now(),
				LocalDate.now().plusDays(10));

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
				//.andExpect(MockMvcResultMatchers.header().string("Location", containsString("/project/2")));
	}

	@Test
	public void testPostProjectWithEmptyFields() throws JsonProcessingException, Exception {
		// Arrange
		CreateProjectDTO projectDTOEmptyField = new CreateProjectDTO("", "Project description", LocalDate.now(),
				LocalDate.now().plusDays(10));

		CreateProjectDTO projectDTONullField = new CreateProjectDTO(null, "Project description", LocalDate.now(),
				LocalDate.now().plusDays(10));

		CreateProjectDTO projectDTONullStartDate = new CreateProjectDTO("Test", "Project description", null,
				LocalDate.now().plusDays(10));

		CreateProjectDTO projectDTONullEndDate = new CreateProjectDTO("Test", "Project description", LocalDate.now(),
				null);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTOEmptyField)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTONullField)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTONullStartDate)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTONullEndDate)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
				//.andExpect(MockMvcResultMatchers.header().string("Location", containsString("/project/1")));
	}
	
	@Test
	public void testGetProject() throws Exception {
		LocalDate now = LocalDate.now();
		Project project = new Project("Pará Bahia e Brasil", "Descrição 123teste get", now, null);
		project = projectRepository.save(project);
		//Agora eu preciso testar o corpo de retorno
		//Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/project/"+project.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pará Bahia e Brasil"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descrição 123teste get"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(now.toString()));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/project/9999"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testGetAllProjects() throws Exception {
		Project project = new Project("Project 1", "Descrição teste get", LocalDate.now(), null);
		Project project2 = new Project("Project 2", "Descrição teste get", LocalDate.now(), null);
		Project project3 = new Project("Teste Get", "Descrição teste get", LocalDate.now(), null);
		Project project4 = new Project("Teste Get", "Descrição teste get", LocalDate.now(), null);
		
		projectRepository.save(project);
		projectRepository.save(project2);
		projectRepository.save(project3);
		projectRepository.save(project4);
		
		//Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/project")) 
//		.param("page", "0")
//        .param("size", "10"))// Tamanho 2 por página
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Project 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Project 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(4));  // No total são 3 projetos
//				.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))  // 2 páginas no total
//    .andExpect(jsonPath("$.size").value(2));
	}
	
	@Test
	public void testGetProjectWithBoards() {
		
	}
}
