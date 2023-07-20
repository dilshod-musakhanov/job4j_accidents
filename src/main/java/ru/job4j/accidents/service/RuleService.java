package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleJdbcTemplate ruleRepository;

    public Optional<Rule> getById(int id) {
        return ruleRepository.getById(id);
    }

    public Set<Rule> getRules(String[] rIds) {
        return ruleRepository.getRules(rIds);
    }

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

}
