package com.revature.ERS2.controllers;

import com.revature.ERS2.models.User;
import com.revature.ERS2.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User u) {
        User createdUser = userService.createUser(u);
        return ResponseEntity.status(201).body(createdUser);
    }


}
