package com.revature.ERS2.services;

import com.revature.ERS2.models.User;

import java.util.List;

public interface UserService {

    /*User login(String username, String password); */

    //TODO: ADAM
    User getUserById(int userId);
    User getUserByUsername(String username);

    //TODO: jonathan
    //added get all users for filtering
    List<User> getAllUsers();

    //User getUserByEmail(String email); REMOVED EMAIL

    //Department has multiple users, changed to list
    List<User> getUsersByDepartment(int departmentId);

    //TODO: Seoky DML
    User createUser(User u);

    //Update role?
    //Delete user?
    //OTHER CRUD
    //updatre

}
