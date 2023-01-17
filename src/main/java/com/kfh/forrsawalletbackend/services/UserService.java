package com.kfh.forrsawalletbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfh.forrsawalletbackend.entities.BankAccount;
import com.kfh.forrsawalletbackend.entities.User;
import com.kfh.forrsawalletbackend.repositories.BankAccountRepository;
import com.kfh.forrsawalletbackend.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return userRepository.save(user);
    }

    public BankAccount createBankAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(0.0);
        return bankAccountRepository.save(bankAccount);
    }
}
