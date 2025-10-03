package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Аутентификация пользователя")
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Проводит аутентификацию пользователя и возвращает JWT " +
            "токен при успешном выполнении")
    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDTO> createAuthToken(@RequestBody JwtRequestDTO jwtRequestDTO){
        String token = authService.authenticate(jwtRequestDTO);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

}
