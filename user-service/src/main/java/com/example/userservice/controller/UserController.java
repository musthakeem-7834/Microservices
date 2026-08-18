package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.dto.UserResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Map<Long, UserDTO> USERS = new HashMap<>();

    static {
        USERS.put(1L, new UserDTO(
                1L,
                "Musthakeem Shaik",
                "musthakeem7834@gmail.com"
        ));

        USERS.put(2L, new UserDTO(
                2L,
                "Rahul",
                "rahul@gmail.com"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        UserDTO user = USERS.get(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}