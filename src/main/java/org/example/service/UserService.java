
package org.example.service;

import org.example.model.User;

import javax.persistence.EntityNotFoundException;

public interface UserService {
    void createUser(User user);

    User getUserById(Long userId);

    void updateUser(User user);

    void deleteUser(Long userId);
}
