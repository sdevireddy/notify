package com.zen.notify.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found");
    }
    
    public UserNotFoundException(String value) {
        super("User with ID " + value + " not found");
    }
}

