package com.kfh.forrsawalletbackend.dto;

import java.util.List;

import com.kfh.forrsawalletbackend.entities.BankAccount;
import com.kfh.forrsawalletbackend.entities.User;

public class AuthResponse {
    private Long id;
    private String username;
    private List<BankAccount> bankAccounts;

    public AuthResponse() {
    }

    public static AuthResponse fromUser(User user) {
        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setBankAccounts(user.getBankAccounts());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

}
