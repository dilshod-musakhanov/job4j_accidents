package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem ruleMem;

    public Optional<Rule> getRuleById(String id) {
        return ruleMem.getRuleById(id);
    }

    public Set<Rule> getRules(String[] rIds) {
        return ruleMem.getRules(rIds);
    }

    public List<Rule> getAll() {
        return ruleMem.getAll();
    }

}
