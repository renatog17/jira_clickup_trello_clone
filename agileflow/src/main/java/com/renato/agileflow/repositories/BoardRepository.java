package com.renato.agileflow.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Page<Board> findAllByExcludedFalse(Pageable pageable);
	Optional<Board> findByIdAndExcludedFalseAndBoardsEmpty(Long id);
	//Page<Project> findAllByExcludedFalse(Pageable pageable);
}
