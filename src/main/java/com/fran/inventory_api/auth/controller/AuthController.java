package com.fran.inventory_api.auth.controller;

import com.fran.inventory_api.auth.dto.AuthResponse;
import com.fran.inventory_api.auth.dto.LoginRequest;
import com.fran.inventory_api.auth.dto.RegisterRequest;
import com.fran.inventory_api.auth.dto.UpdatePasswordRequest;
import com.fran.inventory_api.auth.entity.User;
import com.fran.inventory_api.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}