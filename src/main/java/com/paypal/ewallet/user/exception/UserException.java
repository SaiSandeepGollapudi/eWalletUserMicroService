package com.paypal.ewallet.user.exception;

public class UserException extends RuntimeException{//By extending RuntimeException, UserException becomes an unchecked
    // exception, meaning it does not need to be declared in a method's throws clause.
    private String type;

    private String message;
    public UserException(String type,String message){
        this.type=type;//private String type;: This variable holds the type of the error. It can be used to categorize different types of user-related errors.
        //This variable holds the detailed message about the error, providing more context about what went wrong.
        this.message=message;
    }

    public String getType(){
        return type;
    }

    public String getMessage(){//@Override public String getMessage(): This method overrides the getMessage method from
        // the Throwable class to return the custom error message.

        return message;
    }
}
