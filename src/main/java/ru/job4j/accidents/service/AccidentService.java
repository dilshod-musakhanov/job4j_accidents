package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public Optional<Accident> addAccident(Accident accident) {
        return accidentMem.addAccident(accident);
    }

    public Optional<Accident> getAccidentById(int id) {
        return accidentMem.getAccidentById(id);
    }

    public List<Accident> getAllAccidents() {
        return accidentMem.getAllAccidents().values().stream().toList();
    }

    public boolean updateAccident(Accident accident) {
        return accidentMem.updateAccident(accident);
    }

    public boolean deleteAccidentById(int id) {
        return accidentMem.deleteAccident(id);
    }
}
