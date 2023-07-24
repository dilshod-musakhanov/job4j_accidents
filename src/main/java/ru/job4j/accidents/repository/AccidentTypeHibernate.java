package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {
    private final SessionFactory sf;

    public Optional<AccidentType> save(AccidentType accidentType) {
        try (Session session = sf.openSession()) {
            session.save(accidentType);
            return Optional.of(accidentType);
        }
    }

    public Optional<AccidentType> getById(int id) {
        try (Session session = sf.openSession()) {
            AccidentType accidentType = session.get(AccidentType.class, id);
            return Optional.ofNullable(accidentType);
        }
    }

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM AccidentType", AccidentType.class).list();
        }
    }
}
