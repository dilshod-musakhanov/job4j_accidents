package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentRepository;
    private final RuleJdbcTemplate ruleRepository;
    private final AccidentTypeJdbcTemplate accidentTypeRepository;

    public Optional<Accident> save(Accident accident, String[] rIds) {
        accident.setRules(ruleRepository.getRules(rIds));
        accidentTypeRepository.getById(accident.getType().getId()).ifPresent(accident::setType);
        return accidentRepository.save(accident);
    }

    public Optional<Accident> getById(int id) {
        return accidentRepository.getById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public boolean update(int id, Accident accident, String[] rIds) {
        accident.setRules(ruleRepository.getRules(rIds));
        accidentTypeRepository.getById(accident.getType().getId()).ifPresent(accident::setType);
        return accidentRepository.update(id, accident);
    }

    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }
}
