package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j
public class AccidentTypeHibernate {
    private final CrudRepository crudRepository;

    public Optional<AccidentType> save(AccidentType accidentType) {
        try {
            crudRepository.run(session -> session.persist(accidentType));
            return Optional.of(accidentType);
        } catch (Exception e) {
            log.error("Exception in saving accidentType : " + accidentType + " " + e);
        }
        return Optional.empty();
    }

    public Optional<AccidentType> getById(int id) {
        try {
            return crudRepository.optional(
                    "FROM AccidentType WHERE id = :aId",
                    AccidentType.class,
                    Map.of("aId", id)

            );
        } catch (Exception e) {
            log.error("Exception in finding accidentType by id: " + id + " " + e);
        }
        return Optional.empty();
    }

    public List<AccidentType> findAll() {
        var allAccidentType = crudRepository.query(
                "FROM AccidentType",
                AccidentType.class
        );
        return allAccidentType.isEmpty() ? Collections.emptyList() : allAccidentType;
    }
}
