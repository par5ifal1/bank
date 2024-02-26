package com.example.bankBackend.models;

import com.example.bankBackend.dto.ServicesDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Services extends BaseEntity{
    private String name;
    private String serviceNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "ATM_id", nullable = false)
    private ATM atm;

    @OneToMany(mappedBy = "service")
    private Set<Transactions> transactions;

    public Services(ServicesDTO servicesDTO) {
        this.name = servicesDTO.getName();
        this.serviceNumber = servicesDTO.getServiceNumber();
        this.description = servicesDTO.getDescription();
    }

    public Services(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

