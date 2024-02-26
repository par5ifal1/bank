package com.example.bankBackend.bootstrap;

import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionsBootstrap {
    TransactionsRepository transactionsRepository;

    public TransactionsBootstrap(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Transactional
    public List<Transactions> getAllTransactions(){
        return transactionsRepository.findAll().stream().toList();
    }

    @Transactional
    public List<Transactions> getTransactionsByUserId(Long id){
        return transactionsRepository
                .findAll()
                .stream()
                .filter(x -> x.getUser()
                        .getId()
                        .equals(id)
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public Transactions addTransaction(Transactions transaction){
        return transactionsRepository.save(transaction);
    }

    @Transactional
    public Transactions save(Transactions transaction){return transactionsRepository.save(transaction);}
}
