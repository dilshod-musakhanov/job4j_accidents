package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityRepository;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public Authority findByAuthority(String name) {
        return authorityRepository.findByAuthority(name);
    }
}
