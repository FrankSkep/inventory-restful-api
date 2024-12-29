package com.fran.inventory_api.auth.controller;

import com.fran.inventory_api.auth.entity.Role;
import com.fran.inventory_api.auth.dto.UserRequest;
import com.fran.inventory_api.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

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
