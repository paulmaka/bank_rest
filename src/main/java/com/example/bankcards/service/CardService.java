package com.example.bankcards.service;

import com.example.bankcards.dto.CardRequestDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;

    @Autowired
    public CardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public List<CardResponseDTO> getAllCards() {
        List<Card> allCards = cardRepository.findAll();

        List<CardResponseDTO> allCardsDTO = allCards.stream().map(CardMapper::toCardResponseDTO).toList();
        return allCardsDTO;
    }

    public CardResponseDTO createCard(CardRequestDTO cardRequestDTO) {
        Card card = CardMapper.toAddedCard(cardRequestDTO);
        User user = userService.findById(UUID.fromString(cardRequestDTO.getOwnerId()));
        card.setUser(user);

        Card addedCard = cardRepository.save(card);

        return CardMapper.toCardResponseDTO(addedCard);
    }

    public CardResponseDTO updateCard(UUID id, CardRequestDTO cardRequestDTO) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Карта с таким id не найдена: " + id));

        CardMapper.toUpdatedCard(card, cardRequestDTO);
        if (cardRequestDTO.getOwnerId() != null) {
            User user = userService.findById(UUID.fromString(cardRequestDTO.getOwnerId()));
            card.setUser(user);
        }

        Card updatedCard = cardRepository.save(CardMapper.toUpdatedCard(card, cardRequestDTO));

        return CardMapper.toCardResponseDTO(updatedCard);
    }

    public void deleteCard(UUID id) {
        cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Карта с таким id не найдена: " + id));
        cardRepository.deleteById(id);
    }

    public List<CardResponseDTO> getAllClientCards(String email) {
        List<Card> allClientCards = cardRepository.findAllClientCards(email);

        List<CardResponseDTO> allCardsDTO = allClientCards.stream().map(CardMapper::toCardResponseDTO).toList();
        return allCardsDTO;
    }

    public Long getClientBalance(String email) {
        return cardRepository.getClientBalance(email);
    }

    public Card findByCardNumberToUserWithSuchEmail(String email, String cardNumber) {
        Card card = cardRepository.findByCardNumberToUserWithSuchEmail(email, cardNumber).orElseThrow(() -> new CardNotFoundException("Карта с таким номером у данного пользователя не найдена: " + cardNumber));
        return card;
    }
}
