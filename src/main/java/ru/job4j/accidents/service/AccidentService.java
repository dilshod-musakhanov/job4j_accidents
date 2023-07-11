package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentTypeMemService accidentTypeMemService;
    private final RuleService ruleService;

    public Optional<Accident> addAccident(Accident accident, String[] rIds) {
        accident.setRules(ruleService.getRules(rIds));
        accident.setType(accidentTypeMemService.getAccidentTypeById(accident.getType().getId()).get());
        return accidentMem.addAccident(accident);
    }

    public Optional<Accident> getAccidentById(int id) {
        return accidentMem.getAccidentById(id);
    }

    public List<Accident> getAllAccidents() {
        return accidentMem.getAllAccidents();
    }

    public boolean updateAccident(int id, Accident accident, String[] rIds) {
        accident.setRules(ruleService.getRules(rIds));
        accident.setType(accidentTypeMemService.getAccidentTypeById(accident.getType().getId()).get());
        return accidentMem.updateAccident(id, accident);
    }

    public boolean deleteAccidentById(int id) {
        return accidentMem.deleteAccident(id);
    }
}
