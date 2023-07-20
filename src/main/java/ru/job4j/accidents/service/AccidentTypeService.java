package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeJdbcTemplate accidentTypeRepository;

    public Optional<AccidentType> getAccidentTypeById(int id) {
        return accidentTypeRepository.getById(id);
    }

    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }
}
