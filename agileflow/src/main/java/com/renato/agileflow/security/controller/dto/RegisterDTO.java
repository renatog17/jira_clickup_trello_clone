package com.renato.agileflow.security.controller.dto;

import com.renato.agileflow.security.domain.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}