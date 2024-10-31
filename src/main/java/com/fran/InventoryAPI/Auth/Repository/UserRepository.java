package com.fran.InventoryAPI.Auth.Repository;

import java.util.Optional;

import com.fran.InventoryAPI.Auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}