package com.helpet.appointmentservice.service;

import com.helpet.appointmentservice.service.error.NotFoundLocalizedError;
import com.helpet.appointmentservice.store.model.User;
import com.helpet.appointmentservice.store.repository.UserRepository;
import com.helpet.exception.NotFoundLocalizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExists(UUID userId) {
        return userRepository.existsById(userId);
    }

    public User getUser(UUID userId) throws NotFoundLocalizedException {
        return userRepository.findUserById(userId)
                             .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST));
    }
}
