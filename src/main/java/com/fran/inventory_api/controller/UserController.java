package com.fran.inventory_api.controller;

import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.dto.UserRequest;
import com.fran.inventory_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestParam UserRequest userReq) {
        userReq.setId(id);
        userService.updateUser(userReq);
        return ResponseEntity.ok("User updated sucessfully.");
    }

    @PatchMapping
    public ResponseEntity<Void> updateRole(@PathVariable Long id, @RequestParam Role role) {
        userService.updateRole(id, role);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
