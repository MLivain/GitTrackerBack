package com.ynov.gittracker.repository;

import com.ynov.gittracker.model.UserDao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDao, Integer> {
    UserDao findByUsername(String username);

	Optional<UserDao> findById(String username);
}