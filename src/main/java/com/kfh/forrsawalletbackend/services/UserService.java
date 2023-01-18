package com.kfh.forrsawalletbackend.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfh.forrsawalletbackend.dto.AuthResponse;
import com.kfh.forrsawalletbackend.dto.AuthRequest;
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

    public AuthResponse createUser(AuthRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with this username already exists");
        }
        User user = userRepository.save(User.fromAuth(request));

        return new AuthResponse(user.getId(), user.getUsername());
    }

    public BankAccount createBankAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(0.0);
        return bankAccountRepository.save(bankAccount);
    }

    public AuthResponse signin(@Valid AuthRequest signinRequest) {
        User user = userRepository.findByUsername(signinRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!signinRequest.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new AuthResponse(user.getId(), user.getUsername());
    }
}
