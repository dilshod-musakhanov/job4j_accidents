package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernate;
import ru.job4j.accidents.repository.RuleRepository;
import ru.job4j.accidents.util.ServiceUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Set<Rule> getRules(String[] rIds) {
        return ruleRepository.findByIdIn(ServiceUtil.convertToIntegerSet(rIds));
    }

    public List<Rule> findAll() {
        return (List<Rule>) ruleRepository.findAll();
    }

}
