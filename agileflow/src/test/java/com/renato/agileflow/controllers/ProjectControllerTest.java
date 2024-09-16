package com.renato.agileflow.controllers;
import java.time.LocalDate;

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
import com.renato.agileflow.repositories.ProjectRepository;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ProjectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProjectRepository projectRepository;

	
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

}
