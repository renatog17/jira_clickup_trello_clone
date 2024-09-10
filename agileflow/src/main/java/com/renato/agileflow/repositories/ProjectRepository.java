package com.renato.agileflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
