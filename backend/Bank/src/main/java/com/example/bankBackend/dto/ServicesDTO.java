package com.example.bankBackend.dto;

import com.example.bankBackend.models.Services;

public class ServicesDTO {
    private Long id;
    private String name;
    private String serviceNumber;
    private String description;

    public ServicesDTO(Services services) {
        this.id = services.getId();
        this.name = services.getName();
        this.serviceNumber = services.getServiceNumber();
        this.description = services.getDescription();
    }

    public ServicesDTO(){}

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

    public Long getId() {
        return id;
    }
}
