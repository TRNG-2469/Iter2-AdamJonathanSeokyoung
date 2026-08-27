package com.revature.ERS2.services;

import com.revature.ERS2.dtos.responses.UserResponse;
import com.revature.ERS2.models.User;

import java.util.List;

public interface UserService {

    UserResponse getUserById(Integer userId);
    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    List<UserResponse> getUsersByDepartment(int departmentId);

    //TODO: Jonathan
    User createUser(User u);

}
