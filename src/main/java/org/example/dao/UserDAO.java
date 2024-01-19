package org.example.dao;

import org.example.model.User;

public interface UserDAO {
    void saveUser(User user);
    User getUserById(Long userId);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}
