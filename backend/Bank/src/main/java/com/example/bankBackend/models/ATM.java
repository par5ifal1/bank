package com.example.bankBackend.models;

import com.example.bankBackend.dto.ATMDTO;
import com.example.bankBackend.enums.MaintenanceStatus;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ATM extends BaseEntity{

    @OneToMany(mappedBy = "atm")
    private Set<Bills> bills;

    @OneToMany(mappedBy = "atm")
    private Set<Transactions> transactions;

    @OneToMany(mappedBy = "atm")
    private Set<Services> services;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_status")
    private MaintenanceStatus maintenanceStatus;

    private String ATMKey;

    public ATM(ATMDTO atmdto) {
        this.maintenanceStatus = atmdto.getMaintenanceStatus();
        this.ATMKey = atmdto.getATMKey();
    }

    public ATM(){}

    public MaintenanceStatus getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(MaintenanceStatus maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }



    public String getATMKey() {
        return ATMKey;
    }

    public void setATMKey(String ATMKey) {
        this.ATMKey = ATMKey;
    }

}
