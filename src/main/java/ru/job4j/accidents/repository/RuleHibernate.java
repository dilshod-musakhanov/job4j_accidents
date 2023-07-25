package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
@Log4j
public class RuleHibernate {
    private final CrudRepository crudRepository;

    public Optional<Rule> save(Rule rule) {
        try {
            crudRepository.run(session -> session.persist(rule));
            return Optional.of(rule);
        } catch (Exception e) {
            log.error("Exception in saving rule : " + rule + " " + e);
        }
        return Optional.empty();
    }

    public Optional<Rule> getById(int id) {
        try {
            return crudRepository.optional(
                    "FROM Rule WHERE id = :rId",
                    Rule.class,
                    Map.of("rId", id)

            );
        } catch (Exception e) {
            log.error("Exception in finding rule by id: " + id + " " + e);
        }
        return Optional.empty();
    }

    public Set<Rule> getRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (var id: ids) {
            getById(Integer.parseInt(id)).ifPresent(rules::add);
        }
        return rules;
    }

    public List<Rule> findAll() {
        var allRules = crudRepository.query(
                "FROM Rule",
                Rule.class
        );
        return allRules.isEmpty() ? Collections.emptyList() : allRules;
    }

}
