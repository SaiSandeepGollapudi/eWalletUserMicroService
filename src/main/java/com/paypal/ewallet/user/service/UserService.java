package com.paypal.ewallet.user.service;
import com.paypal.ewallet.user.domain.User;
import com.paypal.ewallet.user.service.resource.TransactionRequest;
import com.paypal.ewallet.user.service.resource.UserResponse;

public interface UserService {

public void createUser(User user);

public UserResponse getUser(String id);// here for id we give String instead of Long as from input by user is in JSON/ String

    public User updateUser(String Id, User updatedUserData) ;

public User deleteUser(String id);

    public boolean userExists(String id) ;

    public boolean transfer(Long userId, TransactionRequest request);


}
