package com.example.bankBackend.repository;

import com.example.bankBackend.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, Long> {
}
