package com.renato.agileflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.renato.agileflow.domain.Usuario;
import com.renato.agileflow.repositories.UsuarioRepository;
import com.renato.agileflow.security.domain.User;
import com.renato.agileflow.security.repository.UserRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	UserRepository userRepository;
	
	public Usuario obterUsuarioAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) userRepository.findByLogin(authentication.getName());
	    Usuario usuario = usuarioRepository.findByUser(user);
	    return usuario;
	}
}
