//package com.renato.agileflow.security.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.renato.agileflow.security.controller.dto.AuthenticationDTO;
//import com.renato.agileflow.security.domain.User;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AuthenticationControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	private void loginTest() {
//		 // Arrange
//		AuthenticationDTO loginRequest = new AuthenticationDTO("user", "password");
//
//	    // O banco de dados já foi populado anteriormente com as credenciais de "user"
//
//	    // Simula o processo de autenticação
//	    Authentication authentication = new UsernamePasswordAuthenticationToken("user", null);
//	    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//	            .thenReturn(authentication);
//
//	    // Simula a geração do token
//	    when(tokenService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");
//
//	    // Act & Assert
//	    mockMvc.perform(MockMvcRequestBuilders.post("/login")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(loginRequest)))
//	            .andExpect(MockMvcResultMatchers.status().isOk())
//	            .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mocked-jwt-token"));
//	}
//	
//	private void registerTest() {
//		//to-do
//	}
//}
