package com.fran.inventory_api.service;

import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.dto.UserRequest;

public interface UserService {
    void updateUser(UserRequest user);

    void updateRole(Long id, Role role);

    void deleteUser(Long id);
}