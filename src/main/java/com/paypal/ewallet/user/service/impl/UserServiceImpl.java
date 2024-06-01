package com.paypal.ewallet.user.service.impl;

import com.paypal.ewallet.user.domain.User;
import com.paypal.ewallet.user.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void createUser(User user) {
        //Check if user is valid
        //check if username exists
        //encode password before storing
        //save user to DB
        //create a

    }

    @Override
    public User getUser(String id) {
        return null;
    }

    @Override
    public User updateUser(String id) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
