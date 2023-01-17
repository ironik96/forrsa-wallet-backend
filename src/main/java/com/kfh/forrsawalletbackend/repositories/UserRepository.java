package com.kfh.forrsawalletbackend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kfh.forrsawalletbackend.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
