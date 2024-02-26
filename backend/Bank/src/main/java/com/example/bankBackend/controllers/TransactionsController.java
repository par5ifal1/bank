package com.example.bankBackend.controllers;

import com.example.bankBackend.bootstrap.*;
import com.example.bankBackend.dto.TransactionsDTO;
import com.example.bankBackend.models.Services;
import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transactions")
@RestController
public class TransactionsController {
    private final TransactionsBootstrap transactionsBootstrap;
    private final ServicesBootstrap servicesBootstrap;
    private final ATMBootstrap atmBootstrap;
    private final UsersBoostrap usersBoostrap;
    private final BillsBootstrap billsBootstrap;

    public TransactionsController(TransactionsBootstrap transactionsBootstrap,
                                  ServicesBootstrap servicesBootstrap,
                                  ATMBootstrap atmBootstrap,
                                  UsersBoostrap usersBoostrap,
                                  BillsBootstrap billsBootstrap) {
        this.transactionsBootstrap = transactionsBootstrap;
        this.servicesBootstrap = servicesBootstrap;
        this.atmBootstrap = atmBootstrap;
        this.usersBoostrap = usersBoostrap;
        this.billsBootstrap = billsBootstrap;
    }

    @PostMapping("/new")
    public ResponseEntity<Transactions> createTransaction(@RequestBody TransactionsDTO transactionsDTO){
        if (transactionsDTO.getUser().getBlocked())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Transactions transaction = new Transactions(transactionsDTO,
                atmBootstrap.getATMById(transactionsDTO.getAtmId()),
                servicesBootstrap.getServiceByID(transactionsDTO.getServiceId()),
                usersBoostrap.getUserByCardNumber(transactionsDTO.getUser().getCardNumber())
        );

        try {
            if(transaction.getService().getId() == 1){

                billsBootstrap.changeBills(
                                billsBootstrap.withdrawCash(
                                        transactionsDTO.getTransferAmount().intValue(),
                                        billsBootstrap.getBillsByATMId(transaction.getAtm().getId())
                                ),
                                transaction.getAtm().getId());
            }
            Users user = usersBoostrap.changeUserBalance(transactionsDTO.getTransferAmount(), transaction.getUser());
            usersBoostrap.save(user);
            transactionsBootstrap.save(transaction);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<Transactions>> getAllTransactionByUserId(@PathVariable("id") Long id){
        return new ResponseEntity<>(transactionsBootstrap.getTransactionsByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Users> updateUserByCardNumber(@RequestParam(value = "cardNumber") Long cardNumber){
        return new ResponseEntity<>(usersBoostrap.getUserByCardNumber(cardNumber), HttpStatus.OK);
    }
}
