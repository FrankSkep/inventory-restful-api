package com.fran.InventoryAPI.auth.Repository;

import java.util.Optional;

import com.fran.InventoryAPI.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}