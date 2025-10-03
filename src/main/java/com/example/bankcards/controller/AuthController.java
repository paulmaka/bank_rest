package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.AuthService;
import com.example.bankcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDTO> createAuthToken(@RequestBody JwtRequestDTO jwtRequestDTO){
        String token = authService.authenticate(jwtRequestDTO);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

}
