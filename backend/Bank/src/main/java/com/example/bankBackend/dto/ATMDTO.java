package com.example.bankBackend.dto;

import com.example.bankBackend.enums.MaintenanceStatus;
import com.example.bankBackend.models.ATM;

public class ATMDTO {
    private MaintenanceStatus maintenanceStatus;
    private String ATMKey;

    public ATMDTO(ATM atm) {
        this.maintenanceStatus = atm.getMaintenanceStatus();
        this.ATMKey = atm.getATMKey();
    }
    public ATMDTO(){}

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
