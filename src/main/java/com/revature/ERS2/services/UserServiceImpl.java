package com.revature.ERS2.services;

import com.revature.ERS2.exceptions.UserNotFoundException;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //Changed to integer because of model, could change to long again if needed
    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    /*
    @Override
    public User getUserByEmail(String email) {
        return null;
    }
     */

    @Override
    public List<User> getUsersByDepartment(int departmentId) {
        return userRepository.getUsersByDepartment(departmentId);
    }

    @Override
    public User createUser(User u) {
        // return userRepository.save(u);
        return u;
    }
}
