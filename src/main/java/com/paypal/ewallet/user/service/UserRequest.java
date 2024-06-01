package com.paypal.ewallet.user.service;

import com.paypal.ewallet.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    //This class is created so that data inserted by user is taken care here

    @NotBlank(message = " username cannot be blank")
    private String name;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @Email(message = "Email should be valid")
    private String email;

    public User toUser(){
return User.builder()
        .name(name)//is same as .name(this.name)
        .password(password)
        .email(email)
        .build();
    }
    // Creates and returns a new Book object with the same attributes as the current book object
    //This method is created to so that data inserted by user is taken care here




}
