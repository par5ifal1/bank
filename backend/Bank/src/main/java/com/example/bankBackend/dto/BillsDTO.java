package com.example.bankBackend.dto;

import com.example.bankBackend.enums.Denomination;
import com.example.bankBackend.models.Bills;

public class BillsDTO {
    private int amount;
    private int denominations;

    public BillsDTO(Bills bills) {
        this.amount = bills.getAmount();
        this.denominations = bills.getDenominations().getValue();
    }

    public BillsDTO(){}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDenominations() {
        return denominations;
    }

    public void setDenominations(int denominations) {
        this.denominations = denominations;
    }
}
