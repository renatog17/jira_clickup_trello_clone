package com.renato.agileflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.agileflow.domain.Usuario;
import com.renato.agileflow.security.domain.User;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	//Page<Project> findAllByExcludedFalse(Pageable pageable);
	Usuario findByUser(User user);
}
