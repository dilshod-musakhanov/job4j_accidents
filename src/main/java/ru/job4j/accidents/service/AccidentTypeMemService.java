package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeMemService {
    private final AccidentTypeMem accidentTypeMem;

    public Optional<AccidentType> getAccidentTypeById(int id) {
        return accidentTypeMem.getAccidentTypeById(id);
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }
}
