package com.example.bankBackend.bootstrap;

import com.example.bankBackend.encryptor.PinEncryptor;
import com.example.bankBackend.models.Transactions;
import com.example.bankBackend.models.Users;
import com.example.bankBackend.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import javax.xml.stream.events.Comment;
import java.util.Objects;

import static com.example.bankBackend.encryptor.PinEncryptor.encryptPin;

@Component
public class UsersBoostrap {

    UsersRepository usersRepository;

    public UsersBoostrap(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Users getUserByCardNumber(Long cardNumber){
        return usersRepository.findAll().stream()
                .filter(x -> Objects.equals(x.getCardNumber(), cardNumber))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Users getUserById(Long id){
        return usersRepository.findAll().stream()
                .filter(x -> Objects.equals(x.getId(), id))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public boolean compareDateAndCVV(Long cardNumber, int date, int cvv){
        Users user = getUserByCardNumber(cardNumber);
        boolean isCoincideCVV = user.getCardCVV() == cvv;
        boolean isCoincideDate = user.getCardDate() == date;

        return isCoincideDate && isCoincideCVV;
    }

    @Transactional
    public boolean comparePIN(Long id, int pin){
        Users user = getUserById(id);
        if (Objects.equals(encryptPin(String.valueOf(pin)), user.getCardPIN())) {
            user.setAttempts((byte) 0);
            return true;
        }

        user.setAttempts((byte) (user.getAttempts() + 1));
        if (user.getAttempts() == 3)
            user.setBlocked(true);

        return false;
    }

    @Transactional
    public Users changeUserBalance(Double cashAmount, Users user){
        if (user.getCurrentBalance() + cashAmount >= 0) {
            user.setCurrentBalance(user.getCurrentBalance() + cashAmount);

            return user;
        }
        throw new IllegalArgumentException();
    }

    @Transactional
    public Users save(Users user){return usersRepository.save(user);}

}
