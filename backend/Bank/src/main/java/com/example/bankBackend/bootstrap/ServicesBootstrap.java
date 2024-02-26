package com.example.bankBackend.bootstrap;

import com.example.bankBackend.models.Services;
import com.example.bankBackend.repository.ServicesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesBootstrap {
    ServicesRepository servicesRepository;

    public ServicesBootstrap(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Transactional
    public List<Services> getAllServices(){
        List<Services> services = servicesRepository.findAll();
        services.remove(0);
        services.remove(0);
        return services;
    }

    @Transactional
    public Services getServiceByID(Long id){
        return servicesRepository
                .findAll()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
