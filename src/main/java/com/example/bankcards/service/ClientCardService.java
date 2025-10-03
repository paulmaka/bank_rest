package com.example.bankcards.service;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.BlockingRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.EmailNotFoundException;
import com.example.bankcards.exception.NotEnoughMoney;
import com.example.bankcards.util.BlockingRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientCardService {

    private final CardService cardService;
    private final UserService userService;
    private final BlockingRequestService blockingRequestService;

    @Autowired
    public ClientCardService(CardService cardService, UserService userService, BlockingRequestService blockingRequestService) {
        this.cardService = cardService;
        this.userService = userService;
        this.blockingRequestService = blockingRequestService;
    }

    public List<CardResponseDTO> getAllCards(String email) {
        return cardService.getAllClientCards(email);
    }

    public BalanceResponseDTO getBalance(String email) {
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        balanceResponseDTO.setBalance(String.valueOf(cardService.getClientBalance(email)));
        return balanceResponseDTO;
    }

    public BlockingResponseDTO blockingCard(String email, BlockingRequestDTO blockingRequestDTO) {
        User user = userService.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Пользователь с таким email не найден: " + email));
        Card card = cardService.findByCardNumberToUserWithSuchEmail(email, blockingRequestDTO.getCardNumber());

        BlockingRequest blockingRequest = new BlockingRequest();
        blockingRequest.setCard(card);
        blockingRequest.setUser(user);

        BlockingRequest addedBlockingRequest = blockingRequestService.save(blockingRequest);

        return BlockingRequestMapper.toDTO(addedBlockingRequest);
    }

    public List<CardResponseDTO> transferMoney(String email, TransferRequestDTO transferRequestDTO) {
        userService.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Пользователь с таким email не найден: " + email));
        Card sourceCard = cardService.findByCardNumberToUserWithSuchEmail(email, transferRequestDTO.getSourceCardNumber());
        Card destinationCard = cardService.findByCardNumberToUserWithSuchEmail(email, transferRequestDTO.getDestinationCardNumber());

        if (sourceCard.getBalance() >= Long.parseLong(transferRequestDTO.getAmount())) {
            sourceCard.setBalance(sourceCard.getBalance() - Long.parseLong(transferRequestDTO.getAmount()));
            destinationCard.setBalance(destinationCard.getBalance() + Long.parseLong(transferRequestDTO.getAmount()));
        } else {
            throw new NotEnoughMoney("Недостаточно средств на карте для перевода.");
        }

        return getAllCards(email);
    }
}
