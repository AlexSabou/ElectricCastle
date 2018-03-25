package service.validator.impl;

import model.User;
import service.validator.Validator;

public class UsernameValidator implements Validator<User> {
    private static final int MIN_LENGTH = 5;
    @Override
    public void validate(User user) {
        if(user.getUsername().length() < MIN_LENGTH)
            throw new IllegalArgumentException("The username must have a minimum length of " + MIN_LENGTH + " characters.");
    }
}
