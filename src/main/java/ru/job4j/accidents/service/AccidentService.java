package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;
import ru.job4j.accidents.util.ServiceUtil;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final RuleRepository ruleRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    public Accident save(Accident accident, String[] rIds) {
        accident.setRules(ruleRepository.findByIdIn(ServiceUtil.convertToIntegerSet(rIds)));
        accidentTypeRepository.findById(accident.getType().getId()).ifPresent(accident::setType);
        return accidentRepository.save(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Accident update(Accident accident, String[] rIds) {
        accident.setRules(ruleRepository.findByIdIn(ServiceUtil.convertToIntegerSet(rIds)));
        accidentTypeRepository.findById(accident.getType().getId()).ifPresent(accident::setType);
        return accidentRepository.save(accident);
    }

    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

}
