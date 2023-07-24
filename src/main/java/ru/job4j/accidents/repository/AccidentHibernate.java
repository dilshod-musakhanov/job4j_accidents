package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    public Optional<Accident> save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.save(accident);
            return Optional.of(accident);
        }
    }

    public Optional<Accident> getById(int id) {
        try (Session session = sf.openSession()) {
            var accident = session.createQuery(
                    "SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :accidentId",
                    Accident.class)
                    .setParameter("accidentId", id)
                    .getSingleResult();
            return Optional.ofNullable(accident);
        }
    }

    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.merge(accident);
            return true;
        }
    }

    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules",
                    Accident.class)
                    .list();
        }
    }

    public boolean delete(int id) {
        try (Session session = sf.openSession()) {
            Accident accident = session.get(Accident.class, id);
            if (accident != null) {
                session.delete(accident);
                return true;
            }
            return false;
        }
    }
}
