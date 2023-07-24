package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final SessionFactory sf;

    public Optional<Rule> save(Rule rule) {
        try (Session session = sf.openSession()) {
            session.save(rule);
            return Optional.of(rule);
        }
    }

    public Optional<Rule> getById(int id) {
        try (Session session = sf.openSession()) {
            Rule rule = session.get(Rule.class, id);
            return Optional.ofNullable(rule);
        }
    }

    public Set<Rule> getRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (var id: ids) {
            getById(Integer.parseInt(id)).ifPresent(rules::add);
        }
        return rules;
    }

    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Rule", Rule.class).list();
        }
    }

}
