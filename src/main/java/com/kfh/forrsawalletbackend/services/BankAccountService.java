package com.kfh.forrsawalletbackend.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfh.forrsawalletbackend.dto.TransferRequest;
import com.kfh.forrsawalletbackend.entities.BankAccount;
import com.kfh.forrsawalletbackend.entities.Transaction;
import com.kfh.forrsawalletbackend.enums.TransactionType;
import com.kfh.forrsawalletbackend.repositories.BankAccountRepository;
import com.kfh.forrsawalletbackend.repositories.TransactionRepository;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public BankAccount deposit(Long accountId, Double amount) {
        // validation
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount should be positive");
        }
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account id"));

        // update account balance
        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        return account;
    }

    public BankAccount withdraw(Long accountId, Double amount) {
        // validation
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount should be positive");
        }
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account id"));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // update account balance
        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.save(account);

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        return account;
    }

    public void transfer(TransferRequest transferRequest) {
        if (transferRequest.getFromAccountId() == null || transferRequest.getToAccountId() == null
                || transferRequest.getAmount() == null) {
            throw new IllegalArgumentException("Missing required fields: fromAccountId, toAccountId, amount");
        }

        if (transferRequest.getFromAccountId().equals(transferRequest.getToAccountId())) {
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }

        if (transferRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid amount, amount should be greater than 0");
        }

        BankAccount fromAccount = bankAccountRepository.findById(transferRequest.getFromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account id"));
        BankAccount toAccount = bankAccountRepository.findById(transferRequest.getToAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account id"));
        Double transferAmount = transferRequest.getAmount();

        // Check if the fromAccount has enough balance
        if (fromAccount.getBalance() < transferAmount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance() - transferAmount);
        toAccount.setBalance(toAccount.getBalance() + transferAmount);

        // Update the accounts
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        // create a transaction record
        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setRelatedAccount(toAccount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(transferAmount);

        transactionRepository.save(transaction);
    }
}
