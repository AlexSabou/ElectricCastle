package service.impl;

import model.User;
import model.UserRole;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;
import service.validator.Validator;
import service.validator.impl.EmailValidator;
import service.validator.impl.PasswordValidator;
import service.validator.impl.UniqueUserValidator;
import service.validator.impl.UsernameValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static User currentUser;
    private UserRepository userRepository;
    private List<Validator<User>> validators;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
        validators = Arrays.asList(new UsernameValidator(), new EmailValidator(), new PasswordValidator(), new UniqueUserValidator(this));
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl(UserRepository userRepository, List<Validator<User>> validators) {
        this.userRepository = userRepository;
        this.validators = validators;
    }

    public UserServiceImpl(List<Validator<User>> validators) {
        this.validators = validators;
    }

    @Override
    public User loginWithCredentials(String username, String password) {
        currentUser = this.userRepository.findByUsernameAndPassword(username, password);
        return currentUser;
    }

    @Override
    public List<User> findAllByRole(UserRole userRole) {
        return this.userRepository.findByUserRole(userRole);
    }

    @Override
    public User add(User user) {
        if(user != null)
            if(user.getId() == null)
                return this.userRepository.save(user);
        return null;
    }

    @Override
    public boolean delete(User user) {
        if(user != null)
            if(user.getId() != null)
                return this.userRepository.delete(user);
        return false;
    }

    @Override
    public User update(User user) {
        if(user != null)
            if(user.getId() != null)
                if(isValidUsernameOrEmail(user))
                    return this.userRepository.update(user);
        return null;
    }

    @Override
    public boolean logout() {
        if(currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean existsUser(User user) {
        return this.userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    @Override
    public boolean isLogged() {
        return (currentUser != null);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void validateUser(User user) throws Exception {
        for(Validator<User> validator: validators)
            validator.validate(user);
    }

    @Override
    public boolean isValidUsernameOrEmail(User user) {
        if(user.getId() != null) {
            if(userRepository.isValidUsernameOrEmail(user))
                return true;
            else
                throw new IllegalArgumentException("A user with the desired username or email already exists.");
        }
        return false;
    }
}
