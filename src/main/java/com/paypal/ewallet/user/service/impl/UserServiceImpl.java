package com.paypal.ewallet.user.service.impl;

import com.paypal.ewallet.user.domain.User;
import com.paypal.ewallet.user.exception.UserException;
import com.paypal.ewallet.user.repository.UserRepository;
import com.paypal.ewallet.user.service.UserService;
import com.paypal.ewallet.user.service.resource.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    @Value("${kafka.topic.user-created}")//The USER_CREATED_TOPIC field is populated with the topic name from the
    // application configuration (application.properties or application.yml).
    private String USER_CREATED_TOPIC;

    @Value("${kafka.topic.user-deleted}")
    private String USER_DELETED_TOPIC;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired//The KafkaTemplate is a helper class that simplifies sending messages to Kafka topics. It is injected
    // into the UserServiceImpl class by Spring.
    KafkaTemplate<String, String>  kafkaTemplate;

    @Override
    public void createUser(User user) {
        //Check if user is valid
        //check if username exists

        Optional<User> userOptional= userRepository.findByName(user.getName());
        if(userOptional.isPresent()){
            throw new UserException("EWALLET_USER_EXISTS_EXCEPTION", "user already exists");
        }

        //encode the password before storing
        user.setPassword(encoder.encode(user.getPassword()));

        //save the user to DB.
        userRepository.save(user);

        //create a user created event. Purpose: When a new user is created, you might want to notify other parts of your system
        // about this event. By sending a message to a Kafka topic, you ensure that other services or components
        // that are interested in this event can react to it
        kafkaTemplate.send(USER_CREATED_TOPIC,String.valueOf(user.getId()));
        //This line sends a message to the Kafka topic named USER_CREATED_TOPIC. The message consists of the user ID
        // converted to a string. The send method of KafkaTemplate has several overloads, this one takes two arguments:
        //The topic to which the message should be sent.The message to be sent, which in this case is the user's ID.
//kafkaTemplate is a medium/ packet through which we send the topic to kafka queue

    }

    @Override
    public UserResponse getUser(String id) {
        Optional<User> userOptional=userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElseThrow(() -> new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION","User not found"));
        return new UserResponse(user);
    }

    @Override
    public User deleteUser(String id) {
        Optional<User> userOptional=userRepository.findById(Long.valueOf(id));
        if(userOptional.isEmpty()){
            throw new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION","User not found");
        }
        userRepository.deleteById(Long.valueOf(id));
        kafkaTemplate.send(USER_DELETED_TOPIC,id);
        return userOptional.get();
    }

    @Override
    public User updateUser(String id) {
        return null;
    }
}
