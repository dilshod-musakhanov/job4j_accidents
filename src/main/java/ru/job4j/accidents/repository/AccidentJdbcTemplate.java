package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.util.AccidentResultSetExtractorUtil;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private static final String INSERT_ACCIDENT = "INSERT INTO accident (name, text, address, accident_type_id) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ACCIDENT_RULE = "INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)";
    private static final String SELECT_ACCIDENT = """
            SELECT a.id, a.name AS a_name, a.text, a.address, at.id AS at_id, at.name AS at_name, r.id AS r_id, r.name AS r_name
            FROM accident a
            JOIN accident_type at ON a.accident_type_id = at.id
            LEFT JOIN accident_rule ar ON a.id = ar.accident_id
            LEFT JOIN rule r ON ar.rule_id = r.id
            WHERE a.id = ?
            """;
    private static final String SELECT_ACCIDENT_LIST = """
            SELECT a.id, a.name AS a_name, a.text, a.address, at.id AS at_id, at.name AS at_name, r.id AS r_id, r.name AS r_name
            FROM accident a
            JOIN accident_type at ON a.accident_type_id = at.id
            LEFT JOIN accident_rule ar ON a.id = ar.accident_id
            LEFT JOIN rule r ON ar.rule_id = r.id
            """;
    private static final String UPDATE_ACCIDENT = "UPDATE accident SET name = ?, text = ?, address = ?, accident_type_id = ? WHERE id = ?";
    private static final String DELETE_ACCIDENT_RULE = "DELETE FROM accident_rule WHERE accident_id = ?";
    private static final String DELETE_ACCIDENT = "DELETE FROM accident WHERE id = ?";

    private final JdbcTemplate jdbc;
    private final AccidentResultSetExtractorUtil rs;

    public Optional<Accident> save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    INSERT_ACCIDENT,
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
                jdbc.update(INSERT_ACCIDENT_RULE,
                        generatedId, rule.getId());
            }
            return Optional.of(accident);
        }
        return Optional.empty();
    }

    public Optional<Accident> getById(int id) {
        List<Accident> accidents = jdbc.query(SELECT_ACCIDENT, ps -> ps.setInt(1, id), rs);
        return accidents.isEmpty() ? Optional.empty() : Optional.of(accidents.get(0));
    }

    public boolean update(int id, Accident accident) {
        boolean updated = jdbc.update(
                UPDATE_ACCIDENT,
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), id) > 0;

        jdbc.update(DELETE_ACCIDENT_RULE, id);

        for (var rule : accident.getRules()) {
            jdbc.update(INSERT_ACCIDENT_RULE, id, rule.getId());
        }
        return updated;
    }

    public List<Accident> findAll() {
        return jdbc.query(SELECT_ACCIDENT_LIST, rs);
    }

    public boolean delete(int id) {
        int rowsDeleted = jdbc.update(DELETE_ACCIDENT);
        return rowsDeleted > 0;
    }

}
