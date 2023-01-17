package com.kfh.forrsawalletbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kfh.forrsawalletbackend.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
