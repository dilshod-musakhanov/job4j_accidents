package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Rule save(Rule rule) {
        jdbc.update("INSERT INTO rule (name) VALUES (?)", rule.getName());
        return rule;
    }

    public Optional<Rule> getById(int id) {
        return jdbc.query("SELECT id, name FROM rule WHERE id = ?",
                List.of(id).toArray(),
                (rs, rowNum) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }).stream().findFirst();
    }

    public Set<Rule> getRules(String[] ids) {
        Set<Rule> result = new HashSet<>();
        for (String id : ids) {
            getById(Integer.parseInt(id)).ifPresent(result::add);
        }
        return result;
    }

    public List<Rule> findAll() {
        return jdbc.query("SELECT id, name FROM rule",
                (rs, rowNum) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
