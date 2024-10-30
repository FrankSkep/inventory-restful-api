package com.fran.Sistema_Inventario.Auth.Repository;

import java.util.Optional;

import com.fran.Sistema_Inventario.Auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}