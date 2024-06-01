package com.paypal.ewallet.user.controller;

import com.paypal.ewallet.user.service.resource.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest){
        userService
return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("user")
    public ResponseEntity getUser(){

    }
}
