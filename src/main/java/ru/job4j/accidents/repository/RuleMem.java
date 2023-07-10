package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem {
    private final Map<Integer, Rule> rules = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public RuleMem() {
        init();
    }

    private void init() {
        var rule = new Rule();
        rule.setId(idGenerator.incrementAndGet());
        rule.setName("Rule 1.1");
        var rule2 = new Rule();
        rule2.setId(idGenerator.incrementAndGet());
        rule2.setName("Rule 2.1");
        var rule3 = new Rule();
        rule3.setId(idGenerator.incrementAndGet());
        rule3.setName("Rule 3.1");

        rules.put(rule.getId(), rule);
        rules.put(rule2.getId(), rule2);
        rules.put(rule3.getId(), rule3);
    }

    public Optional<Rule> getRuleById(int id) {
        Rule rule = rules.get(id);
        return Optional.ofNullable(rule);
    }

    public List<Rule> getAllRules() {
        return rules.values().stream().toList();
    }
}
