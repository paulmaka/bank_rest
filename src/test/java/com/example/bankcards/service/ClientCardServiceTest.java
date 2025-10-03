package com.example.bankcards.service;

import com.example.bankcards.dto.BlockingRequestDTO;
import com.example.bankcards.dto.BlockingResponseDTO;
import com.example.bankcards.entity.BlockingRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClientCardServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private CardService cardService;
    @Mock
    private BlockingRequestService blockingRequestService;
    @InjectMocks
    private ClientCardService clientCardService;

    @Test
    void createBlockingRequestSuccessfully() {

        // Arrange
        String email = "test@example.com";
        String cardNumber = "1234 5678 9101 1121";
        String maskedCardNumber = "**** **** **** 1121";

        BlockingRequestDTO blockingRequestDTO = new BlockingRequestDTO();
        blockingRequestDTO.setCardNumber(cardNumber);

        User user = new User();
        user.setEmail(email);

        when(userService.findByEmail(email)).thenReturn(Optional.of(user));

        Card card = new Card();
        card.setCardNumber(cardNumber);

        when(cardService.findByCardNumberToUserWithSuchEmail(email, blockingRequestDTO.getCardNumber())).thenReturn(card);

        when(blockingRequestService.save(any(BlockingRequest.class))).thenAnswer(
                i -> {
                    BlockingRequest blockingRequest = i.getArgument(0);
                    blockingRequest.setId(UUID.randomUUID());
                    return blockingRequest;
                });

        // Act
        BlockingResponseDTO blockingResponseDTO = clientCardService.blockingCard(email, blockingRequestDTO);

        // Assert
        assertNotNull(blockingResponseDTO.getId());
        assertEquals(maskedCardNumber, blockingResponseDTO.getCardNumber());
        assertEquals(email, blockingResponseDTO.getCardHolderEmail());
    }

}
