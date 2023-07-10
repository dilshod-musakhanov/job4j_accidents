package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public AccidentTypeMem() {
        init();
    }

    private void init() {
        var accidentType1 = new AccidentType();
        accidentType1.setId(idGenerator.incrementAndGet());
        accidentType1.setName("Two vehicles");
        var accidentType2 = new AccidentType();
        accidentType2.setId(idGenerator.incrementAndGet());
        accidentType2.setName("Vehicle and pedestrian");
        var accidentType3 = new AccidentType();
        accidentType3.setId(idGenerator.incrementAndGet());
        accidentType3.setName("Vehicle and bicycle");

        accidentTypes.put(accidentType1.getId(), accidentType1);
        accidentTypes.put(accidentType2.getId(), accidentType2);
        accidentTypes.put(accidentType3.getId(), accidentType3);
    }

    public Optional<AccidentType> getAccidentTypeById(int id) {
        AccidentType accidentType = accidentTypes.get(id);
        return Optional.ofNullable(accidentType);
    }

    public List<AccidentType> getAllAccidentType() {
        return accidentTypes.values().stream().toList();
    }

}
