package com.example.bankcards.dto;


import com.example.bankcards.dto.validators.CreateCardValidationGroup;
import jakarta.validation.constraints.NotBlank;

public class CardRequestDTO {

    @NotBlank(groups = CreateCardValidationGroup.class, message = "Номер карты не должен быть пустым.")
    private String cardNumber;
    @NotBlank(groups = CreateCardValidationGroup.class, message = "Номер владельца карты не должен быть пустым.")
    private String ownerId;
    @NotBlank(groups = CreateCardValidationGroup.class, message = "Время истечения срока карты не должно быть пустым.")
    private String expirationDate;
    private String status;

    public CardRequestDTO() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String owner) {
        this.ownerId = owner;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
