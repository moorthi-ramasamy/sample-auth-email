package com.bcbsm.interview.userauth.service;

import com.bcbsm.interview.userauth.entity.User;

import java.util.List;

/**
 * @author Moorthi Ramasamy
 */
public interface UserService {

    /**
     * Fetch available active users from DB
     * @return List<Users>
     */
    List<User> getUsers();

    /**
     * Authenticate the user by taking userName and password
     * and validate against DB.
     * @param userName
     * @param password
     * @return
     */
    User authenticate(String userName, String password);
}
