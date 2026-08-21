package com.revature.ERS2.services;

import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByDepartment(int departmentId) {
        return null;
    }

    @Override
    public User createUser(User u) {
        // return userRepository.save(u);
        return u;
    }
}
