package com.renato.agileflow.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.renato.agileflow.domain.Board;
import com.renato.agileflow.domain.Project;

//@SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
@Transactional
public class ProjectRepositoryTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() {

	}

	@Test
	public void testFindAllByExcludedFalse() {
		Project project1 = new Project("projeto teste", "descricao projeto teste", LocalDate.now(),
				LocalDate.now().plusDays(60));
		Project project2 = new Project("projeto teste 2", "descricao projeto teste 2", LocalDate.now(),
				LocalDate.now().plusDays(60));
		Project project3 = new Project("projeto teste 3", "descricao projeto teste 3", LocalDate.now(),
				LocalDate.now().plusDays(60));
		project1.setExcluded(false);
		project2.setExcluded(false);
		project3.setExcluded(true);
		entityManager.persist(project1);
		entityManager.persist(project2);
		entityManager.persist(project3);

		Pageable pageable = PageRequest.of(0, 10);
		Page<Project> result = projectRepository.findAllByExcludedFalse(pageable);

		assertThat(result.getContent()).contains(project1);
		assertThat(result.getContent()).contains(project2);
		assertThat(result.getContent()).doesNotContain(project3);
	}

	@Test
	public void testFindByIdAndExcludedFalse() {
		Project project1 = new Project("projeto teste", "descricao projeto teste", LocalDate.now(),
				LocalDate.now().plusDays(60));
		Project project2 = new Project("projeto teste 2", "descricao projeto teste 2", LocalDate.now(),
				LocalDate.now().plusDays(60));
		Project project3 = new Project("projeto teste 3", "descricao projeto teste 3", LocalDate.now(),
				LocalDate.now().plusDays(60));
		project1.setExcluded(false);
		project2.setExcluded(false);
		project3.setExcluded(true);
		
		entityManager.persist(project1);
	    entityManager.persist(project2);
	    entityManager.persist(project3);
		
		Optional<Project> test1 = projectRepository.findByIdAndExcludedFalse(project1.getId());
		Optional<Project> test2 = projectRepository.findByIdAndExcludedFalse(project2.getId());
		Optional<Project> test3 = projectRepository.findByIdAndExcludedFalse(project3.getId());
		
		assertThat(test1).isPresent().contains(project1);
	    assertThat(test2).isPresent().contains(project2);
	    assertThat(test3).isNotPresent();
	}
	
	@Test
	public void testFindByIdAndExcludedFalseAndBoardsEmpty() {
		Project project1 = new Project("projeto teste", "descricao projeto teste", LocalDate.now(),
	            LocalDate.now().plusDays(60));
	    Project project2 = new Project("projeto teste 2", "descricao projeto teste 2", LocalDate.now(),
	            LocalDate.now().plusDays(60));
	    Project project3 = new Project("projeto teste 3", "descricao projeto teste 3", LocalDate.now(),
	            LocalDate.now().plusDays(60));
	    
	    Board board1 = new Board(project1, "Board 1", "aa", LocalDateTime.now(), "aa", "aa", "aa");
	    Board board2 = new Board(project1, "Board 2", "aa", LocalDateTime.now(), "aa", "aa", "aa");
	    Board board3 = new Board(project1, "Board 3", "aa", LocalDateTime.now(), "aa", "aa", "aa");
	    
	    ArrayList<Board> boards1 = new ArrayList<>();
	    ArrayList<Board> boards2 = new ArrayList<>();
	    ArrayList<Board> boards3 = new ArrayList<>();
	    
//	    boards1.add(board1);
//	    boards2.add(board2);
	    boards3.add(board3);
	    
	    project1.setBoards(boards1); 
	    project2.setBoards(boards2); 
	    project3.setBoards(boards3);
	    
	    project1.setExcluded(false);
	    project2.setExcluded(false);
	    project3.setExcluded(true);
	    
	    Optional<Project> test1 = projectRepository.findByIdAndExcludedFalseAndBoardsEmpty(project1.getId());
	    Optional<Project> test2 = projectRepository.findByIdAndExcludedFalseAndBoardsEmpty(project2.getId());
	    Optional<Project> test3 = projectRepository.findByIdAndExcludedFalseAndBoardsEmpty(project3.getId());
	    
	    assertThat(test1).isPresent().contains(project1); // project1 deve ser encontrado
	    assertThat(test2).isPresent().contains(project2); // project2 deve ser encontrado

	    // Assert para verificar que o projeto com 'excluded = true' não foi encontrado
	    assertThat(test3).isNotPresent(); // project3 não deve ser encontrado

	    // Verifique que os projetos não têm boards
	    // Assumindo que você já adicionou a lógica para inicializar as listas de boards como vazias:
	    assertThat(test1).isPresent().hasValueSatisfying(project -> assertThat(project.getBoards()).isEmpty());
	    assertThat(test2).isPresent().hasValueSatisfying(project -> assertThat(project.getBoards()).isEmpty());


	}
}
