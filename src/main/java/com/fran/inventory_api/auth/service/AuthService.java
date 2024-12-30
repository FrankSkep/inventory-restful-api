package com.fran.inventory_api.auth.service;

import com.fran.inventory_api.auth.dto.AuthResponse;
import com.fran.inventory_api.auth.dto.LoginRequest;
import com.fran.inventory_api.auth.dto.RegisterRequest;
import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.auth.entity.User;
import com.fran.inventory_api.auth.exception.AuthenticationException;
import com.fran.inventory_api.auth.exception.UserNotFoundException;
import com.fran.inventory_api.auth.jwt.JwtService;
import com.fran.inventory_api.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found."));
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (
                Exception e) {
            throw new AuthenticationException("Incorrect user or password.");
        }
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthenticationException("User already exists.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}