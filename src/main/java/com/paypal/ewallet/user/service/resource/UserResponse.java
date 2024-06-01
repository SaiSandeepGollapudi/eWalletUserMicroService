package com.paypal.ewallet.user.service.resource;

import com.paypal.ewallet.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    // whenever you're giving a response which are of non-connected entities.
    // You should never connect them in your entity class. You will always connect them in your response class like this when
    // you're not using mapping like oneToMany etc
//Data Transfer Object (DTO): The UserResponse class is a DTO used to send data to clients.
//Decoupling: Separates the internal entity structure (User) from the response structure (UserResponse).
    //Custom Formatting: Allows for custom formatting or transformations, such as converting id to a string.
    //API Stability: Provides a stable API response format that can remain consistent even if the User entity changes.
    private String id;
    private String name;
    private String email;

    public UserResponse(User user){

        this.id= user.getId().toString();

        this.name=user.getName();
        this.email=user.getEmail();

    }
}
