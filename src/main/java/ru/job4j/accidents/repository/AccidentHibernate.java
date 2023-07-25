package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j
public class AccidentHibernate {
    private final CrudRepository crudRepository;

    public Optional<Accident> save(Accident accident) {
        try {
            crudRepository.run(session -> session.persist(accident));
            return Optional.of(accident);
        } catch (Exception e) {
            log.error("Exception in saving accident : " + accident + " " + e);
        }
        return Optional.empty();
    }

    public Optional<Accident> getById(int id) {
        try {
            return crudRepository.optional("SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :aId",
                    Accident.class,
                    Map.of("aId", id)
            );
        } catch (Exception e) {
            log.error("Exception in getting accident by id : " + id + " " + e);
        }
        return Optional.empty();
    }

    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.merge(accident));
            return true;
        } catch (Exception e) {
            log.error("Exception in updating accident " + e);
        }
        return false;
    }

    public List<Accident> findAll() {
        var allAccidents = crudRepository.query("SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules",
                Accident.class);
        return allAccidents.isEmpty() ? Collections.emptyList() : allAccidents;
    }

    public boolean delete(int id) {
        try {
            crudRepository.run(
                    "DELETE FROM Accident WHERE id =  :aId",
                    Map.of("aId", id)
            );
            return true;
        } catch (Exception e) {
            log.error("Exception in deleting accident by id: " + id + " " + e);
        }
        return false;
    }
}
