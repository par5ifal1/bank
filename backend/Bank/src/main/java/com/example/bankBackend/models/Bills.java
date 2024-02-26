package com.example.bankBackend.models;

import com.example.bankBackend.dto.BillsDTO;
import com.example.bankBackend.enums.Denomination;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"denominations", "ATM_id"}))
public class Bills extends BaseEntity {
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    private Denomination denominations;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "ATM_id", nullable = false)
    private ATM atm;

    public Bills(BillsDTO billsDTO) {
        this.denominations = Denomination.getDenominationByValue(billsDTO.getDenominations());
        this.amount = billsDTO.getAmount();
    }

    public Bills(){}

    public ATM getAtm() {
        return atm;
    }

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public Denomination getDenominations() {
        return denominations;
    }

    public void setDenominations(Denomination denominations) {
        this.denominations = denominations;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
