package com.paypal.ewallet.user.controller;

import com.paypal.ewallet.user.domain.User;
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

    @GetMapping("user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
        UserResponse userResponse=userService.getUser(id);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
        User user=userService.deleteUser(id);
        return new ResponseEntity<>(new UserResponse(user),HttpStatus.OK);
    }



    @PutMapping("/user")//PUT is used to update or replace an existing resource.
//    PUT does not create a new resource if it's not already present; it typically returns an error if the resource does not exist.
    public ResponseEntity<User> updateUser(@RequestParam("userId") String id, @RequestBody @Valid UserRequest userRequest){
        if (!userService.userExists(id)) {
            // Return a response with HTTP status NOT_FOUND if the book does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.updateUser(id, userRequest.toUser()),HttpStatus.OK);
    }

    @PostMapping("/user/{user-id}/transfer")
    public ResponseEntity<?> performTransaction(@PathVariable("user-id") String userId, @RequestBody @Valid TransactionRequest transactionRequest) {
    }
}
