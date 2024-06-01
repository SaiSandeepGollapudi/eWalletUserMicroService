package com.paypal.ewallet.user.service;
import com.paypal.ewallet.user.domain.User;

public interface UserService {

public void createUser(User user);

public User getUser(String id);// here for id we give String instead of Long as from input by user is in JSON/ String

public User updateUser(String id);

public void deleteUser(String id);



}
