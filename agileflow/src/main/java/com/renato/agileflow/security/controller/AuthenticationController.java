package com.renato.agileflow.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.agileflow.domain.Usuario;
import com.renato.agileflow.repositories.UsuarioRepository;
import com.renato.agileflow.security.controller.dto.AuthenticationDTO;
import com.renato.agileflow.security.controller.dto.LoginResponseDTO;
import com.renato.agileflow.security.controller.dto.RegisterDTO;
import com.renato.agileflow.security.domain.User;
import com.renato.agileflow.security.repository.UserRepository;
import com.renato.agileflow.security.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	//agora eu tenho que salvar o nome e cpf na tabela de usuario
	@PostMapping("/register")
	@Transactional
	public ResponseEntity register(@RequestBody RegisterDTO data) {
		if(this.userRepository.findByLogin(data.login()) != null)
			return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, data.role());

		Usuario usuario = new Usuario(data.name(), data.endereco(), newUser);
		this.userRepository.save(newUser);
		newUser.setUsuario(usuario);
		
		this.usuarioRepository.save(usuario);
		
		return ResponseEntity.ok().build();
	}
	//eu estava criando o novo endpoint
}