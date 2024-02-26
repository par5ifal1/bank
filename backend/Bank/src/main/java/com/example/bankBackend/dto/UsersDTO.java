package com.example.bankBackend.dto;

import com.example.bankBackend.models.Users;

public class UsersDTO {
    private Boolean isBlocked;
    private String name;
    private String surname;
    private Long cardNumber;
    private int cardDate;
    private int cardCVV;
    private String cardPIN;
    private byte attempts;
    private Double currentBalance;

    public UsersDTO(Users users) {
        this.name = users.getName();
        this.surname = users.getSurname();
        this.cardNumber = users.getCardNumber();
        this.cardDate = users.getCardDate();
        this.cardCVV = users.getCardCVV();
        this.cardPIN = users.getCardPIN();
        this.currentBalance = users.getCurrentBalance();
        this.isBlocked = users.getBlocked();
    }

    public UsersDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardDate() {
        return cardDate;
    }

    public void setCardDate(int cardDate) {
        this.cardDate = cardDate;
    }

    public int getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardPIN() {
        return cardPIN;
    }

    public void setCardPIN(String cardPIN) {
        this.cardPIN = cardPIN;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public byte getAttempts() {
        return attempts;
    }

    public void setAttempts(byte attempts) {
        this.attempts = attempts;
    }
}
