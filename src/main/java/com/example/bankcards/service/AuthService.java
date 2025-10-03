package com.example.bankcards.service;

import com.example.bankcards.dto.JwtRequestDTO;
import com.example.bankcards.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(JwtRequestDTO jwtRequestDTO) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.getEmail(), jwtRequestDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Введены некорректные данные пользователя " + e.getMessage());
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequestDTO.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return token;
    }

}
