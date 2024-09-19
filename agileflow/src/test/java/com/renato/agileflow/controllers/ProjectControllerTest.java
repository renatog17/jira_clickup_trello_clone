package com.renato.agileflow.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renato.agileflow.controllers.dto.CreateProjectDTO;
import com.renato.agileflow.controllers.dto.UpdateProjectDTO;
import com.renato.agileflow.domain.Board;
import com.renato.agileflow.domain.Project;
import com.renato.agileflow.repositories.BoardRepository;
import com.renato.agileflow.repositories.ProjectRepository;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectControllerTest {
//O banco não está sendo meio que resetado, ou seja, o id não começa do zero e eu ainda estou 
//	pensando em uma maneira de fazer isso

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BoardRepository boardRepository;

	@Test
	public void testPostProject() throws Exception {
		// Arrange
		CreateProjectDTO projectDTO = new CreateProjectDTO("New Project", "Project description", LocalDate.now(),
				LocalDate.now().plusDays(10));

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(projectDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		// .andExpect(MockMvcResultMatchers.header().string("Location",
		// containsString("/project/2")));
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
		// .andExpect(MockMvcResultMatchers.header().string("Location",
		// containsString("/project/1")));
	}

	@Test
	public void testGetProject() throws Exception {
		LocalDate now = LocalDate.now();
		Project project = new Project("Pará Bahia e Brasil", "Descrição 123teste get", now, null);
		project = projectRepository.save(project);
		// Agora eu preciso testar o corpo de retorno
		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/project/" + project.getId()))
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

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/project"))
//		.param("page", "0")
//        .param("size", "10"))// Tamanho 2 por página
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Project 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Project 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(4)); // No total são 3 projetos
//				.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))  // 2 páginas no total
//    .andExpect(jsonPath("$.size").value(2));
	}

	@Test
	public void testGetProjectWithBoards() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		Project project = new Project("Projeto com boards", "null", LocalDate.now(), null);
		Board board = new Board(project, "Board 11111", "Descricao board1", now, "label", "priority", "cor");
		Board board1 = new Board(project, "Board 2", "Descricao board2", now, "label", "priority", "cor");
		project.setBoards(Arrays.asList(board, board1));

		// entity maneger ou o jpa exigiu que fosse adicionado um método setBoards
		projectRepository.save(project);
		boardRepository.save(board);
		boardRepository.save(board1);
		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/project/1/boards"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Projeto com boards"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.boards").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.boards.length()").value(2))
				// verificar se a a ordem em que foram inseridos os boards é a mesma em que
				// serão buscados
				.andExpect(MockMvcResultMatchers.jsonPath("$.boards[0].name").value("Board 11111"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.boards[1].name").value("Board 2"));
	}

	@Test
	public void testDeleteProject() throws Exception {
		// Arrange 1
		Project project = new Project("Projeto a ser deletado", "Descrição do projeto que vai ser deletado",
				LocalDate.now(), null);
		project = projectRepository.save(project);
		// Arrange 2
		Project project2 = new Project("Projeto a ser deletado", "Descrição do projeto que vai ser deletado",
				LocalDate.now(), null);
		project2.setExcluded(true);
		project2 = projectRepository.save(project2);

		// Arrange 3
		Project project3 = new Project("Erro em cascata",
				"Projeto que não pode ser deletado pois vai gerar erro em cascata", LocalDate.now(), null);
		Board board = new Board(project, "Board 11111", "Descricao board1", LocalDateTime.now(), "label", "priority",
				"cor");
		Board board1 = new Board(project, "Board 2", "Descricao board2", LocalDateTime.now(), "label", "priority",
				"cor");
		project3.setBoards(Arrays.asList(board, board1));
		projectRepository.save(project3);
		boardRepository.save(board);
		boardRepository.save(board1);
		// Act & Assert 1
		mockMvc.perform(MockMvcRequestBuilders.delete("/project/" + project.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		// Act & Assert 2
		mockMvc.perform(MockMvcRequestBuilders.delete("/project/" + project2.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		// Act & Assert 3
		mockMvc.perform(MockMvcRequestBuilders.delete("/project/" + project3.getId()))
				.andExpect(MockMvcResultMatchers.status().isConflict());

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.delete("/project/9999"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testUpdateProject() throws Exception {
		Project project = new Project("atualizar", "atualizar", LocalDate.now(), null);
		project = projectRepository.save(project);

		UpdateProjectDTO updateProjectDTO = new UpdateProjectDTO("descricao atualizada");
		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.put("/project/" + project.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateProjectDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.put("/project/9").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateProjectDTO)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
