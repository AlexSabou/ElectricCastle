package service.validator.impl;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import service.validator.Validator;

import java.io.InvalidObjectException;

public class UniqueUserValidator implements Validator<User> {
    private UserService userService;

    public UniqueUserValidator() {
        userService = new UserServiceImpl();
    }

    public UniqueUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validate(User user) throws InvalidObjectException {
        if(user.getId() == null)
            if(userService.existsUser(user))
                throw new InvalidObjectException("The user already exists.");
    }
}
