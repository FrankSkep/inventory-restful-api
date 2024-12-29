package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.auth.entity.User;
import com.fran.inventory_api.auth.repository.UserRepository;
import com.fran.inventory_api.dto.UserRequest;
import com.fran.inventory_api.exception.UserNotFoundException;
import com.fran.inventory_api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUser(UserRequest user) {
        User userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userEntity.setUsername(user.getUsername());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setCountry(user.getCountry());
        userRepository.save(userEntity);
    }

    @Override
    public void updateRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
