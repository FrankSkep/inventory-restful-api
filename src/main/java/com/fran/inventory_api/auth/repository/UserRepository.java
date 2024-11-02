package com.fran.inventory_api.auth.repository;

import java.util.Optional;

import com.fran.inventory_api.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}