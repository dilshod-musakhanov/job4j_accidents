package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentTypeMem;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentTypeMem accidentTypeMem;
    private final RuleMem ruleMem;

    public Optional<Accident> addAccident(Accident accident, String[] rIds) {
        accident.setRules(ruleMem.getRules(rIds));
        accident.setType(accidentTypeMem.getAccidentTypeById(accident.getType().getId()).get());
        return accidentMem.addAccident(accident);
    }

    public Optional<Accident> getAccidentById(int id) {
        return accidentMem.getAccidentById(id);
    }

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public boolean updateAccident(int id, Accident accident, String[] rIds) {
        accident.setRules(ruleMem.getRules(rIds));
        accident.setType(accidentTypeMem.getAccidentTypeById(accident.getType().getId()).get());
        return accidentMem.updateAccident(id, accident);
    }

    public boolean deleteAccidentById(int id) {
        return accidentMem.deleteAccident(id);
    }
}
