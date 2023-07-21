package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {

    private static final String INSERT_ACCIDENT_TYPE = "INSERT INTO accident_type (name) VALUES (?)";
    private static final String SELECT_ACCIDENT_TYPE = "SELECT id, name FROM accident_type WHERE id = ?";
    private static final String SELECT_ACCIDENT_TYPE_LIST = "SELECT id, name FROM accident_type";

    private final JdbcTemplate jdbc;

    public Optional<AccidentType> save(AccidentType accidentType) {
        jdbc.update(INSERT_ACCIDENT_TYPE, accidentType.getName());
        return Optional.of(accidentType);
    }

    public Optional<AccidentType> getById(int id) {
        return jdbc.query(SELECT_ACCIDENT_TYPE, List.of(id).toArray(),
                (rs, rowNum) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                }).stream().findFirst();
    }

    public List<AccidentType> findAll() {
        return jdbc.query(SELECT_ACCIDENT_TYPE_LIST,
                (rs, rowNum) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

}
