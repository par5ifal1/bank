package com.example.bankBackend.repository;

import com.example.bankBackend.models.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BillsRepository extends JpaRepository<Bills,Long> {
}
