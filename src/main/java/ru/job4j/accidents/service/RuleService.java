package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem ruleMem;

    public Optional<Rule> getRuleById(String id) {
        return ruleMem.getRuleById(Integer.parseInt(id));
    }

    public List<Rule> getAllRules() {
        return ruleMem.getAllRules();
    }
}
