package com.paypal.ewallet.user.service.impl;

import com.paypal.ewallet.user.domain.User;
import com.paypal.ewallet.user.exception.UserException;
import com.paypal.ewallet.user.repository.UserRepository;
import com.paypal.ewallet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    KafkaTemplate<String, String>  kafkaTemplate;

    @Override
    public void createUser(User user) {
        //Check if user is valid
        //check if username exists
        /

        Optional<User> userOptional= userRepository.findByName(user.getName());
        if(userOptional.isPresent()){
            throw new UserException("EWALLET_USER_EXISTS_EXCEPTION", "user already exists");
            throw new UserException("\"EWALLET_USER_EXISTS_EXCEPTION", "user already exists");
        }

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
