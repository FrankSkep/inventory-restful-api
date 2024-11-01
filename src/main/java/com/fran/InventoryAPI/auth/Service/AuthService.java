package com.fran.InventoryAPI.auth.Service;

import com.fran.InventoryAPI.auth.DTO.AuthResponse;
import com.fran.InventoryAPI.auth.DTO.LoginRequest;
import com.fran.InventoryAPI.auth.DTO.RegisterRequest;
import com.fran.InventoryAPI.auth.Entity.Role;
import com.fran.InventoryAPI.auth.Entity.User;
import com.fran.InventoryAPI.auth.Exception.AuthenticationException;
import com.fran.InventoryAPI.auth.JWT.JwtService;
import com.fran.InventoryAPI.auth.Repository.UserRepository;
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
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationException("Usuario o contraseña incorrectos");
        }
    }

    public AuthResponse register(RegisterRequest request) {
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