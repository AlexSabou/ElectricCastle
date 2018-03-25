package repository;

import model.User;
import model.UserRole;

import java.util.List;

public interface UserRepository {
    public List<User> findAll();
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findByUserRole(UserRole userRole);
    public User findByUsernameAndPassword(String username, String password);
    public User save(User user);
    public User update(User user);
    public boolean delete(User user);
    public boolean existsByUsernameOrEmail(String username, String email);
    public boolean isValidUsernameOrEmail(User user);
}
