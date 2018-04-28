package com.login.user.service;

import com.login.user.model.User;
import com.login.auth.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link UserService}.
 * Which is interacting with MongoDb.
 *
 * @author Jemin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User find(String email) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("emailAddress").is(email)), User.class);
    }

    @Override
    public User save(User user) {
        try {
            user.setPassword(PasswordUtil.getSaltedHash(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoTemplate.save(user);
        return user;
    }
}
