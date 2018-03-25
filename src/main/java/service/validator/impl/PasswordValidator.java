package service.validator.impl;

import model.User;
import service.validator.Validator;

public class PasswordValidator implements Validator<User> {
    private static final int MIN_LENGTH = 6;
    @Override
    public void validate(User user) {
        if(user.getPassword().length() < MIN_LENGTH)
            throw new IllegalArgumentException("The password must have a minimum length of " + MIN_LENGTH + " characters.");
    }
}
