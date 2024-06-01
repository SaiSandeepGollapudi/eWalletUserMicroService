package com.paypal.ewallet.user.controller;

import com.paypal.ewallet.user.service.UserService;
import com.paypal.ewallet.user.service.resource.UserRequest;
import com.paypal.ewallet.user.service.resource.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest){
        userService.createUser(userRequest.toUser());
return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("user/{id}")
//    public ResponseEntity getUser(@PathVariable ("id") String id){
//UserResponse userResponse= userService.getUser(id);
//
//    }
}
