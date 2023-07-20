package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Optional<Accident> save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO accident (name, text, address, accident_type_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);

        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        if (keyList != null && keyList.size() > 0) {
            int generatedId = (int) keyList.get(0).get("id");
            accident.setId(generatedId);

            for (var rule : accident.getRules()) {
                jdbc.update("INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)",
                        generatedId, rule.getId());
            }
            return Optional.of(accident);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Accident> getById(int id) {
        String sql = """
                SELECT a.id, a.name AS a_name, a.text, a.address, at.id AS at_id, at.name AS at_name
                FROM accident a
                JOIN accident_type at ON a.accident_type_id = at.id
                WHERE a.id = ?
                """;
        List<Accident> accidents = jdbc.query(sql, ps -> ps.setInt(1, id),
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("a_name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("at_id"));
                    accidentType.setName(rs.getString("at_name"));
                    accident.setType(accidentType);
                    return accident;
                });
        if (accidents.isEmpty()) {
            return Optional.empty();
        }

        Accident accident = accidents.get(0);

        String sqlRule = """
                SELECT r.id AS rule_id, r.name AS r_name FROM rule r
                JOIN accident_rule ar ON r.id = ar.rule_id
                WHERE ar.accident_id = ?
                """;
        List<Rule> rules = jdbc.query(sqlRule, ps -> ps.setInt(1, accident.getId()),
                (rs, rowNum) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    rule.setName(rs.getString("r_name"));
                    return rule;
                });
        accident.setRules(new HashSet<>(rules));
        return Optional.of(accident);
    }

    public boolean update(int id, Accident accident) {
        boolean updated = jdbc.update(
                "UPDATE accident SET name = ?, text = ?, address = ?, accident_type_id = ? WHERE id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), id) > 0;

        jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", id);

        for (var rule : accident.getRules()) {
            jdbc.update("INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)", id, rule.getId());
        }
        return updated;
    }

    public List<Accident> findAll() {
        String sql = """
            SELECT a.id, a.name AS a_name, a.text, a.address, at.id AS at_id, at.name AS at_name, r.id AS r_id, r.name AS r_name
            FROM accident a
            JOIN accident_type at ON a.accident_type_id = at.id
            LEFT JOIN accident_rule ar ON a.id = ar.accident_id
            LEFT JOIN rule r ON ar.rule_id = r.id
            """;
        Map<Integer, Accident> accidentsMap = new HashMap<>();
        return jdbc.query(sql, (rs) -> {
            while (rs.next()) {
                int accidentId = rs.getInt("id");
                Accident accident = accidentsMap.computeIfAbsent(accidentId, key -> {
                    Accident newAccident = new Accident();
                    newAccident.setId(key);
                    try {
                        newAccident.setName(rs.getString("a_name"));
                        newAccident.setText(rs.getString("text"));
                        newAccident.setAddress(rs.getString("address"));
                        AccidentType accidentType = new AccidentType();
                        accidentType.setId(rs.getInt("at_id"));
                        accidentType.setName(rs.getString("at_name"));
                        newAccident.setType(accidentType);
                        newAccident.setRules(new HashSet<>());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return newAccident;
                });
                int ruleId = rs.getInt("r_id");
                if (ruleId != 0) {
                    var rule = new Rule();
                    rule.setId(ruleId);
                    rule.setName(rs.getString("r_name"));
                    accident.getRules().add(rule);
                }
            }
            return new ArrayList<>(accidentsMap.values());
        });
    }

    public boolean delete(int id) {
        int rowsDeleted = jdbc.update("DELETE FROM accident WHERE id = ?", id);
        return rowsDeleted > 0;
    }

}
