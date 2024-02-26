package com.example.bankBackend.repository;

import com.example.bankBackend.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
}
