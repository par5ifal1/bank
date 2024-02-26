package com.example.bankBackend.bootstrap;

import com.example.bankBackend.enums.MaintenanceStatus;
import com.example.bankBackend.models.ATM;
import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.repository.ATMRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.bankBackend.encryptor.PinEncryptor.encryptPin;

@Component
public class ATMBootstrap {
    ATMRepository atmRepository;

    public ATMBootstrap(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Transactional
    public ATM getATMById(Long id){
        return atmRepository.findAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public boolean compareATMKey(Long id, int key){
        return Objects.equals(encryptPin(String.valueOf(key)), getATMById(id).getATMKey());
    }

    @Transactional
    public ATM closeATM(Long id){
        ATM atm = getATMById(id);
        atm.setMaintenanceStatus(MaintenanceStatus.OPERATIONAL);
        return atm;
    }

    @Transactional
    public ATM save(ATM atm){return atmRepository.save(atm);}

}
