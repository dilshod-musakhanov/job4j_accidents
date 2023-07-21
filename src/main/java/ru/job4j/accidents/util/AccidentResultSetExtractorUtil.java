package ru.job4j.accidents.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public final class AccidentResultSetExtractorUtil implements ResultSetExtractor<List<Accident>> {

    private AccidentResultSetExtractorUtil() {

    }

    @Override
    public List<Accident> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Accident> accidentsMap = new HashMap<>();
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
                Rule rule = new Rule();
                rule.setId(ruleId);
                rule.setName(rs.getString("r_name"));
                accident.getRules().add(rule);
            }
        }

        return new ArrayList<>(accidentsMap.values());
    }
}
