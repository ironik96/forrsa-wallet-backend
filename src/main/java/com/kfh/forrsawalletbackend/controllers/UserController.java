package com.kfh.forrsawalletbackend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.forrsawalletbackend.dto.AuthResponse;
import com.kfh.forrsawalletbackend.dto.AuthRequest;
import com.kfh.forrsawalletbackend.entities.BankAccount;
import com.kfh.forrsawalletbackend.entities.User;
import com.kfh.forrsawalletbackend.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest user) {
        AuthResponse response = userService.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody AuthRequest signinRequest) {
        AuthResponse response = userService.signin(signinRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/bank-account")
    public ResponseEntity<BankAccount> createBankAccount(@PathVariable Long userId) {
        BankAccount bankAccount = userService.createBankAccount(userId);
        return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
    }
}