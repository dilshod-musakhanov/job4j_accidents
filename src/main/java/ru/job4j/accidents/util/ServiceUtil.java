package ru.job4j.accidents.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class ServiceUtil {

    private ServiceUtil() {

    }

    public static Set<Integer> convertToIntegerSet(String[] rIds) {
        return Arrays.stream(rIds)
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
    }
}
