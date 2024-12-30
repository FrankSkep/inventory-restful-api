package com.fran.inventory_api.auth.service;

import com.fran.inventory_api.auth.dto.PasswordRequest;
import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.auth.entity.User;
import com.fran.inventory_api.auth.exception.IncorrectPasswordException;
import com.fran.inventory_api.auth.repository.UserRepository;
import com.fran.inventory_api.auth.dto.UserRequest;
import com.fran.inventory_api.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public void updateUser(String username, UserRequest user) {
        User userEntity = getByUsername(username);

        userEntity.setUsername(user.getUsername());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setCountry(user.getCountry());
        userRepository.save(userEntity);
    }

    public void updateRole(Long id, Role role) {
        User user = getById(id);
        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    public void updatePassword(String username, PasswordRequest password) {
        User user = getByUsername(username);

        if (passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password.getNewPassword()));
        } else {
            throw new IncorrectPasswordException("Old password does not match.");
        }
        userRepository.save(user);
    }
}
