package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.ClientCardService;
import com.example.bankcards.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientCardController {

    private final JwtUtil jwtUtil;
    private final ClientCardService clientCardService;

    @Autowired
    public ClientCardController(JwtUtil jwtUtil, ClientCardService clientCardService) {
        this.jwtUtil = jwtUtil;
        this.clientCardService = clientCardService;
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDTO>> getAllCards(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.getAllCards(email));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.getBalance(email));
    }

    @PostMapping("/blocking-requests")
    public ResponseEntity<BlockingResponseDTO> blockingCard(@RequestHeader("Authorization") String authHeader, @RequestBody BlockingRequestDTO blockingRequestDTO) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.blockingCard(email, blockingRequestDTO));
    }

    @PutMapping("/transfer")
    public ResponseEntity<List<CardResponseDTO>> transferMoney(@RequestHeader("Authorization") String authHeader, @RequestBody TransferRequestDTO transferRequestDTO) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.transferMoney(email, transferRequestDTO));
    }

}
