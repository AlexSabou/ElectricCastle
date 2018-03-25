package service;

import model.User;
import model.UserRole;
import service.validator.Validator;

import java.util.List;

public interface UserService {
    public User loginWithCredentials(String username, String password);
    public boolean logout();
    public boolean isLogged();
    public List<User> findAllByRole(UserRole userRole);
    public User getCurrentUser();
    public User add(User user);
    public boolean delete(User user);
    public User update(User user);
    public void validateUser(User user) throws Exception;
    public boolean existsUser(User user);
    public boolean isValidUsernameOrEmail(User user);
}
