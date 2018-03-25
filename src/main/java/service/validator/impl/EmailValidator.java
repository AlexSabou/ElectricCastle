package service.validator.impl;

import model.User;
import service.validator.Validator;

public class EmailValidator implements Validator<User> {

    @Override
    public void validate(User user) {
        String email = user.getEmail();
        if(!email.contains("@yahoo.com") && !email.contains("@gmail.com") && !email.contains("@student.utcluj.ro") || email.isEmpty())
            throw new IllegalArgumentException("Invalid email address.Please use one of these mail services: @yahoo.com | @gmail.com | @student.utcluj.ro");
    }
}
