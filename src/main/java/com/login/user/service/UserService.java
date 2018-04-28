package com.login.user.service;

import com.login.user.model.User;

/**
 * Service that represent that different operations for user.
 *
 * @author Jemin
 */
public interface UserService {

    /**
     * Find user with given email.
     *
     * @param email : Email address.
     * @return : User.
     */
    User find(String email);

    /**
     * Save user.
     *
     * @param user : {@link User}
     * @return : User.
     */
    User save(User user);
}
