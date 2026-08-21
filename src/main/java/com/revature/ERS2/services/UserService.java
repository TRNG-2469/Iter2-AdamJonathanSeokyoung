package com.revature.ERS2.services;

import com.revature.ERS2.models.User;

public interface UserService {

    /*User login(String username, String password); */

    //TODO: ADAM
    User getUserById(int userId);
    User getUserByUsername(String username);

    //TODO: jonathan
    User getUserByEmail(String email);
    User getUserByDepartment(int departmentId);

    //TODO: Seoky DML
    User createUser(User u);

    //Update role?
    //Delete user?
    //OTHER CRUD
    //updatre

}
