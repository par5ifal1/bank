package com.example.bankBackend.dto;

import com.example.bankBackend.TimestampDeserializer.TimestampDeserializer;
import com.example.bankBackend.models.ATM;
import com.example.bankBackend.models.Services;
import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.models.Users;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionsDTO {
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp date;
    private String transferInfo;
    private Double transferAmount;
    private Long atmId;
    private Users user;
    private Long serviceId;


    public TransactionsDTO(Transactions transactions) {
        this.date = transactions.getDate();
        this.transferInfo = transactions.getTransferInfo();
        this.transferAmount = transactions.getTransferAmount();
        this.atmId = transactions.getAtm().getId();
        this.serviceId = transactions.getService().getId();
        this.user = transactions.getUser();
    }

    public TransactionsDTO(){}

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(String transferInfo) {
        this.transferInfo = transferInfo;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getAtmId() {
        return atmId;
    }

    public void setAtmId(Long atmId) {
        this.atmId = atmId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
