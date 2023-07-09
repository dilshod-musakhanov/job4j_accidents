package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final AccidentTypeMem accidentTypeMem = new AccidentTypeMem();

    public AccidentMem() {
        init();
    }

    private void init() {
        Accident accident1 = new Accident();
        accident1.setId(idGenerator.incrementAndGet());
        accident1.setName("Accident1");
        accident1.setText("Minor accident");
        accident1.setAddress("Sheikh Zayed Road");
        accident1.setType(accidentTypeMem.getAccidentTypeById(1).get());

        Accident accident2 = new Accident();
        accident2.setId(idGenerator.incrementAndGet());
        accident2.setName("Accident 2");
        accident2.setText("Major accident");
        accident2.setAddress("Garhoud Bridge");
        accident2.setType(accidentTypeMem.getAccidentTypeById(2).get());

        accidents.put(accident1.getId(), accident1);
        accidents.put(accident2.getId(), accident2);
    }

    public Optional<Accident> addAccident(Accident accident) {
        int id = idGenerator.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return Optional.of(accident);
    }

    public Optional<Accident> getAccidentById(int id) {
        Accident accident = accidents.get(id);
        return Optional.ofNullable(accident);
    }

    public List<Accident> getAllAccidents() {
        return accidents.values().stream().toList();
    }

    public boolean updateAccident(int id, Accident accident) {
        return accidents.computeIfPresent(id, (k, v) -> {
            v.setName(accident.getName());
            v.setText(accident.getText());
            v.setAddress(accident.getAddress());
            v.setType(accident.getType());
            return v;
        }) != null;
    }

    public boolean deleteAccident(int id) {
        Accident accident = accidents.remove(id);
        return accident != null;
    }
}
