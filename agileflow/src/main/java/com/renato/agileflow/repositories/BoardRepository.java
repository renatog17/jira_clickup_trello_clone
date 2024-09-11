package com.renato.agileflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	//Page<Project> findAllByExcludedFalse(Pageable pageable);
}
