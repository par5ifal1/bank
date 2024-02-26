package com.example.bankBackend.repository;

import com.example.bankBackend.models.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ATMRepository extends JpaRepository<ATM,Long> {
}
