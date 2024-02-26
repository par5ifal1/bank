package com.example.bankBackend.models;

import com.example.bankBackend.dto.UsersDTO;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Users extends BaseEntity{
    private Boolean isBlocked;
    private String name;
    private String surname;
    private Long cardNumber;
    private int cardDate;
    private int cardCVV;
    private String cardPIN;
    private byte attempts;
    private Double currentBalance;

    @OneToMany(mappedBy = "user")
    private Set<Transactions> transactions;

    public Users(UsersDTO userDTO) {
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.cardNumber = userDTO.getCardNumber();
        this.cardDate = userDTO.getCardDate();
        this.cardCVV = userDTO.getCardCVV();
        this.cardPIN = userDTO.getCardPIN();
        this.currentBalance = userDTO.getCurrentBalance();
        this.attempts = userDTO.getAttempts();
        this.isBlocked = userDTO.getBlocked();
    }

    public Users(){}

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

    public Set<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transactions> transactions) {
        this.transactions = transactions;
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
