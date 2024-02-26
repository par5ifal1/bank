package com.example.bankBackend.models;

import com.example.bankBackend.dto.TransactionsDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Transactions extends BaseEntity{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date;
    private String transferInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ATM_id", nullable = false)
    @JsonIgnore
    private ATM atm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id", nullable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Service_id", nullable = false)
    @JsonIgnore
    private Services service;

    private Double transferAmount;

    public Transactions(TransactionsDTO transactionsDTO, ATM atm, Services service, Users user) {
        this.date = transactionsDTO.getDate();
        this.transferInfo = transactionsDTO.getTransferInfo();
        this.transferAmount = transactionsDTO.getTransferAmount();
        this.atm = atm;
        this.service = service;
        this.user = user;
    }

    public Transactions(){}

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

    public Users getUser() {return user;}

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public ATM getAtm() {
        return atm;
    }

    public Services getService() {
        return service;
    }
}
