package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Map<Long, UserDTO> USERS = new HashMap<>();

    static {
        USERS.put(1L, new UserDTO(1L, "Musthakeem", "musthakeem@example.com"));
        USERS.put(2L, new UserDTO(2L, "Rahul", "rahul@example.com"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {

        UserDTO user = USERS.get(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);
    }
}
