package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        if (user.getName() == null || user.getEmail() == null || userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid user data");
        }

        userRepository.save(user);
    }

    @Override
    public User getUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(new java.util.function.Supplier<EntityNotFoundException>() {
                    @Override
                    public EntityNotFoundException get() {
                        return new EntityNotFoundException("User not found with id: " + userId);
                    }
                });
    }




    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
