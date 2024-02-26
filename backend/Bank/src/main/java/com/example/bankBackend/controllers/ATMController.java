package com.example.bankBackend.controllers;

import com.example.bankBackend.bootstrap.ATMBootstrap;
import com.example.bankBackend.bootstrap.BillsBootstrap;
import com.example.bankBackend.bootstrap.UsersBoostrap;
import com.example.bankBackend.dto.*;
import com.example.bankBackend.models.ATM;
import com.example.bankBackend.models.Bills;
import com.example.bankBackend.models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/")
@RestController
public class ATMController {
    private final ATMBootstrap atmBootstrap;
    private final BillsBootstrap billsBootstrap;
    private final UsersBoostrap usersBoostrap;

    public ATMController(ATMBootstrap atmBootstrap, BillsBootstrap billsBootstrap, UsersBoostrap usersBoostrap) {
        this.atmBootstrap = atmBootstrap;
        this.billsBootstrap = billsBootstrap;
        this.usersBoostrap = usersBoostrap;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ATMDTO> getATM(@PathVariable("id") Long id){
        ATM atm = atmBootstrap.getATMById(id);
        ATMDTO atmdto = new ATMDTO(atm);
        return new ResponseEntity<>(atmdto, HttpStatus.OK);
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillsDTO>> getBills(@PathVariable("id") Long id){
        List<Bills> bills = billsBootstrap.getBillsByATMId(id);
        List<BillsDTO> billsDTOS = bills.stream().map(BillsDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(billsDTOS, HttpStatus.OK);
    }

    @GetMapping("/compare")
    public ResponseEntity<Boolean> compareATMKey(@RequestParam(value = "id") Long id,
                                                 @RequestParam(value = "atmKey") int atmKey){
        return new ResponseEntity<>(atmBootstrap.compareATMKey(id, atmKey), HttpStatus.OK);
    }

    @PostMapping("/{id}/postBills")
    public ResponseEntity<List<Bills>> putBills(@RequestBody ListOfBillsDTO billsDTOList, @PathVariable("id") Long id){
        List<Bills> bills = billsDTOList.getBillsDTOList().stream().map(Bills::new).toList();
        List<Bills> finallyBills;

        try {
            finallyBills = billsBootstrap.changeBills(bills, id);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(finallyBills, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Boolean>> getUserByCardNumber(@RequestParam(value = "cardNumber") Long cardNumber,
                                                       @RequestParam(value = "cardDate") int cardDate,
                                                       @RequestParam(value = "cardCVV") int cardCVV){
        List<Boolean> booleanList = new ArrayList<>();
        Users user;
        try {
            user = usersBoostrap.getUserByCardNumber(cardNumber);
        }catch (IllegalArgumentException e){
            booleanList.add(false);
            booleanList.add(false);
            return new ResponseEntity<>(booleanList, HttpStatus.BAD_REQUEST);
        }

        if (usersBoostrap.compareDateAndCVV(cardNumber, cardDate, cardCVV)) {
            if (user.getBlocked()) {
                booleanList.add(true);
                booleanList.add(true);
                return new ResponseEntity<>(booleanList, HttpStatus.OK);
            }
            booleanList.add(true);
            booleanList.add(false);
            return new ResponseEntity<>(booleanList, HttpStatus.OK);
        }
        booleanList.add(true);
        booleanList.add(false);

        return new ResponseEntity<>(booleanList, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/pin")
    public ResponseEntity<UsersDTO> comparePIN(@RequestParam(value = "id") Long id,
                                               @RequestParam(value = "pin") int pin){
        Users users = usersBoostrap.getUserByCardNumber(id);

        if (!usersBoostrap.comparePIN(users.getId(), pin) || users.getBlocked()) {
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setBlocked(users.getBlocked());
            return new ResponseEntity<>(usersDTO, HttpStatus.BAD_REQUEST);
        }

        UsersDTO usersDTO = new UsersDTO(users);
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Boolean> closeATM(@PathVariable("id") Long id){
        atmBootstrap.save(atmBootstrap.closeATM(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
