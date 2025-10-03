package com.example.bankcards.controller;

import com.example.bankcards.dto.CardRequestDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.dto.validators.CreateCardValidationGroup;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Взаимодействие с картами, доступное для администратора")
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Получение списка всех карт")
    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getAllCards() {
        List<CardResponseDTO> cardResponseDTOs = cardService.getAllCards();

        return ResponseEntity.ok(cardResponseDTOs);
    }

    @Operation(summary = "Создание новой карты")
    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(
            @Validated({CreateCardValidationGroup.class})
            @RequestBody CardRequestDTO cardRequestDTO) {
        CardResponseDTO cardResponseDTO = cardService.createCard(cardRequestDTO);

        return ResponseEntity.ok(cardResponseDTO);
    }

    @Operation(summary = "Обновление данных карты")
    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCard(
            @Validated({Default.class})
            @PathVariable String id, @RequestBody CardRequestDTO cardRequestDTO) {
        UUID uuid = UUID.fromString(id);

        CardResponseDTO cardResponseDTO = cardService.updateCard(uuid, cardRequestDTO);

        return ResponseEntity.ok(cardResponseDTO);
    }

    @Operation(summary = "Удаление существующей карты")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        cardService.deleteCard(uuid);

        return ResponseEntity.noContent().build();
    }
}
