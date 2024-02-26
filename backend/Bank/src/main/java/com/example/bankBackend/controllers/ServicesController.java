package com.example.bankBackend.controllers;

import com.example.bankBackend.bootstrap.ServicesBootstrap;
import com.example.bankBackend.dto.ServicesDTO;
import com.example.bankBackend.models.Services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/services")
@RestController
public class ServicesController {
    private final ServicesBootstrap servicesBootstrap;

    public ServicesController(ServicesBootstrap servicesBootstrap) {
        this.servicesBootstrap = servicesBootstrap;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServicesDTO>> getAllServices() {
        List<Services> services = servicesBootstrap.getAllServices();
        List<ServicesDTO> servicesDTOS = services.stream().map(ServicesDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(servicesDTOS, HttpStatus.OK);
    }
}
