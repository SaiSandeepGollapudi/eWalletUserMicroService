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

//    @Override
//    public User updateUser(String id) {
//
//
//        return null;
//    }

    @Override
    public User updateUser(String Id, User updatedUserData) {
        Optional<User> optionalOriginalUser = userRepository.findById(Long.valueOf(Id));// retrieve the original book entity from the repository based on the provided Id using
        // the findById method of the bookRepository. The result is wrapped in an Optional to handle cases where the book may not exist.
//        The Optional<Book> type was introduced in Java 8  to deal with potentially null values in a safer and more concise manner
//Presence of Value:If a value of type Book is present, it is wrapped inside an Optional<Book> instance using the of() method or other factory methods provided by the Optional class.
// Absence of a Value: If the value is absent (i.e., null), an empty Optional<Book> instance is created using the empty() method.
//                Avoiding NullPointerExceptions:
//        Optional<Book> helps prevent NullPointerExceptions by providing methods to safely access and manipulate the value without directly exposing it.
//        Operations on Optional:
//        Optional<Book> provides various methods to perform operations on the wrapped value, such as get(), isPresent(), orElse(), orElseGet(), orElseThrow(), etc.
        if (optionalOriginalUser.isPresent()) { // Check if the original book entity exists in the repository
            // If the original book exists, update it with the provided book entity
            // This assumes that the book entity passed as an argument contains the updated data
            User originalUser = optionalOriginalUser.get();
            originalUser.setEmail(updatedUserData.getEmail());
            originalUser.setPassword(encoder.encode(updatedUserData.getPassword()));
            originalUser.setName(updatedUserData.getName());
            return userRepository.save(originalUser);


        }else {
            // Handle the case where the book with the provided ID does not exist
            return null;
        }
    }

    public boolean userExists(String id) {//   Return a response with HTTP status NOT_FOUND if the book does not exist

        return userRepository.existsById(Long.valueOf(id));
    }

}
