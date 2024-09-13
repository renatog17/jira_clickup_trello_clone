package com.renato.agileflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	//Page<Project> findAllByExcludedFalse(Pageable pageable);
}
