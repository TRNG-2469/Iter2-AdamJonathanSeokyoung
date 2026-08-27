package com.revature.ERS2.services;

import com.revature.ERS2.dtos.responses.UserResponse;
import com.revature.ERS2.exceptions.DepartmentNotFoundException;
import com.revature.ERS2.exceptions.UserNotFoundException;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.DepartmentRepository;
import com.revature.ERS2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserServiceImpl(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<UserResponse> getAllUsers(){
        return transformUserToResponse(userRepository.findAll());
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return transformUserToResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UserNotFoundException(username));

        return transformUserToResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByDepartment(int departmentId) {
        departmentRepository.findById(departmentId).
                orElseThrow( () -> new DepartmentNotFoundException(departmentId));

        return transformUserToResponse(userRepository.getUsersByDepartment_DepartmentId(departmentId));
    }

    @Override
    public User createUser(User u) {
        // return userRepository.save(u);
        return u;
    }

    /**
     * Helper method to make user objects into responses (cuts password, only returns department id)
     */
    public static UserResponse transformUserToResponse(User u) {
        return new UserResponse(u.getId(), u.getFirstName(), u.getFirstName(), u.getUsername(),
            u.getRole(), u.getDepartment().getDepartmentId()
        );
    }

    /**
     * Transforms list of users to list of UserResponse
     */
    public static List<UserResponse> transformUserToResponse(List<User> userList) {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User u : userList) {
            userResponseList.add(transformUserToResponse(u));
        }
        return userResponseList;
    }


}
