package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.ClientCardService;
import com.example.bankcards.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Взаимодействие со своими картами, доступное пользователю")
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

    @Operation(summary = "Получение списка всех карт пользователя")
    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDTO>> getAllCards(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.getAllCards(email));
    }

    @Operation(summary = "Получение информации о балансе пользователя")
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.getBalance(email));
    }

    @Operation(summary = "Отправление запроса на блокировку карты")
    @PostMapping("/blocking-requests")
    public ResponseEntity<BlockingResponseDTO> blockingCard(@RequestHeader("Authorization") String authHeader, @RequestBody BlockingRequestDTO blockingRequestDTO) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.blockingCard(email, blockingRequestDTO));
    }

    @Operation(summary = "Перевод денег между картами")
    @PutMapping("/transfer")
    public ResponseEntity<List<CardResponseDTO>> transferMoney(@RequestHeader("Authorization") String authHeader, @RequestBody TransferRequestDTO transferRequestDTO) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(jwt);

        return ResponseEntity.ok(clientCardService.transferMoney(email, transferRequestDTO));
    }

}
