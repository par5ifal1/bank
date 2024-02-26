package com.example.bankBackend.bootstrap;

import com.example.bankBackend.dto.BillsDTO;
import com.example.bankBackend.enums.Denomination;
import com.example.bankBackend.models.Bills;
import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.repository.BillsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BillsBootstrap {
    BillsRepository billsRepository;

    public BillsBootstrap(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    @Transactional
    public List<Bills> getAllBills(){
        return billsRepository.findAll();
    }

    @Transactional
    public Bills findBillsByDenomination(Denomination denomination){
        return billsRepository.findAll()
                .stream()
                .filter(x -> x.getDenominations() == denomination)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public List<Bills> changeBills(List<Bills> bills, Long id){
        List<Bills> currentBills = billsRepository
                .findAll()
                .stream()
                .filter(x -> x.getAtm().getId().equals(id))
                .peek(
                        x -> x.setAmount(
                                x.getAmount() + bills.stream()
                                        .filter(
                                                y -> y.getDenominations() == x.getDenominations()
                                        )
                                        .findFirst()
                                        .orElse(new Bills())
                                        .getAmount()
                        )
                ).toList();

        billsRepository.saveAll(currentBills);
        return currentBills;
    }

    @Transactional
    public List<Bills> withdrawCash(int amount, List<Bills> availableBills) {
        List<Bills> result = new ArrayList<>();
        amount = Math.abs(amount);

        availableBills.sort((b1, b2) -> Integer.compare(
                b2.getDenominations().getValue(),
                b1.getDenominations().getValue()
                )
        );
        for (Bills bill : availableBills) {
            int denominationValue = bill.getDenominations().getValue();
            int count = Math.min(amount / denominationValue, bill.getAmount());

            if (count > 0) {
                Bills currentBill = new Bills();
                currentBill.setAmount(-count);
                currentBill.setDenominations(Denomination.getDenominationByValue(denominationValue));
                result.add(currentBill);
                amount -= count * denominationValue;
            }

            if (amount == 0) {
                break;
            }
        }

        if (amount > 0) {
            result.clear();
            throw new IllegalArgumentException();
        }

        return result;
    }

    @Transactional
    public List<Bills> getBillsByATMId(Long id){
        return billsRepository
                .findAll().stream()
                .filter(x -> x.getAtm().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Transactional
    public Bills save(Bills bills){return billsRepository.save(bills);}
}
