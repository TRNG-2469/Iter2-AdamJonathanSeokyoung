package com.revature.ERS2.controllers;

import com.revature.ERS2.dtos.CreateUserDto;
import com.revature.ERS2.models.User;
import com.revature.ERS2.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(
            @RequestParam(required = false) Integer department_id){
        if(department_id != null){
            return userService.getUsersByDepartment(department_id);
        }
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto u) {
        User createdUser = userService.createUser(u);
        return ResponseEntity.status(201).body(createdUser);
    }


}
