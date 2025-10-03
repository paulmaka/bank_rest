package com.example.bankcards.util;

import com.example.bankcards.dto.CardRequestDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.Card;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CardMapper {

    public static CardResponseDTO toCardResponseDTO(Card card) {
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        String fp = card.getCardNumber().substring(0, 15);
        String sp = card.getCardNumber().substring(15);
        fp = fp.replaceAll("\\d", "*");
        cardResponseDTO.setCardNumber(fp + sp);
        cardResponseDTO.setOwnerId(String.valueOf(card.getUser().getId()));
        cardResponseDTO.setExpirationDate(String.valueOf(card.getExpirationDate()));
        cardResponseDTO.setStatus(card.getStatus());
        cardResponseDTO.setBalance(String.valueOf(card.getBalance()));

        return cardResponseDTO;
    }

    public static Card toAddedCard(CardRequestDTO cardRequestDTO) {
        Card card = new Card();
        card.setCardNumber(cardRequestDTO.getCardNumber());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth ym = YearMonth.parse(cardRequestDTO.getExpirationDate(), dtf);
        LocalDate expirationDate = ym.atEndOfMonth();
        card.setExpirationDate(expirationDate);

        return card;
    }

    public static Card toUpdatedCard(Card card, CardRequestDTO cardRequestDTO) {
        if (cardRequestDTO.getCardNumber() != null) {
            card.setCardNumber(cardRequestDTO.getCardNumber());
        }
        if (cardRequestDTO.getExpirationDate() != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth ym = YearMonth.parse(cardRequestDTO.getExpirationDate(), dtf);
            LocalDate expirationDate = ym.atEndOfMonth();
            card.setExpirationDate(expirationDate);
        }
        if (cardRequestDTO.getStatus() != null) {
            card.setStatus(cardRequestDTO.getStatus());
        }
        return card;
    }

}
