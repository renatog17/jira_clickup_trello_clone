package com.renato.agileflow.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	Page<Project> findAllByExcludedFalse(Pageable pageable);
	Optional<Project> findByIdAndExcludedFalse(Long id);
	Optional<Project> findByIdAndExcludedFalseAndBoardsEmpty(Long id);
}
