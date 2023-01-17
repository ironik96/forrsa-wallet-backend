package com.kfh.forrsawalletbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.forrsawalletbackend.dto.AmountRequest;
import com.kfh.forrsawalletbackend.dto.TransferRequest;
import com.kfh.forrsawalletbackend.entities.BankAccount;
import com.kfh.forrsawalletbackend.services.BankAccountService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<BankAccount> deposit(@PathVariable Long accountId, @RequestBody AmountRequest amount) {
        BankAccount updatedAccount = bankAccountService.deposit(accountId, amount.getAmount());
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<BankAccount> withdraw(@PathVariable Long accountId, @RequestBody AmountRequest amount) {
        BankAccount updatedAccount = bankAccountService.withdraw(accountId, amount.getAmount());
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest transferRequest) {
        bankAccountService.transfer(transferRequest);
        return new ResponseEntity<>("Money transferred successfully", HttpStatus.OK);
    }
}
