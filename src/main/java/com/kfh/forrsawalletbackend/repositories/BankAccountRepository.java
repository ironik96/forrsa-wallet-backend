package com.kfh.forrsawalletbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kfh.forrsawalletbackend.entities.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
