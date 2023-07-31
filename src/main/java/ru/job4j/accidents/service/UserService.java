package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        try {
            var savedUser = userRepository.save(user);
            result = Optional.of(savedUser);
        } catch (Exception e) {
            log.error("Exception in saving user " + e);
        }
        return result;
    }
}
