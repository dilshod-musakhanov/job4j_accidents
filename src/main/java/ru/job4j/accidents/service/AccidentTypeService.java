package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeHibernate accidentTypeRepository;

    public Optional<AccidentType> getById(int id) {
        return accidentTypeRepository.getById(id);
    }

    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }
}
