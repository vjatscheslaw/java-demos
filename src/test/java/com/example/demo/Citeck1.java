package com.example.demo;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Citeck1 {

    List<Integer> numberz = new ArrayList<>();

    @Before
    public void init() {
     int i = 250;
        while (i > 0) {
            --i;
            numberz.add((int) Math.round(Math.random()* 10));
        }
    }

    @Test
    public void usingStreamz() {
        Map<Integer, Long> finalResult = new LinkedHashMap<>();
        Map<Integer, Long> result = numberz.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        result.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue())
                .forEachOrdered(e -> finalResult.put(e.getKey(), e.getValue()));
        finalResult.forEach((k, v) -> System.out.print("Число " + k + " встречается " + v + " раз\n"));
    }

    @Test
    public void noStreamz() {
        Map<Integer, AtomicInteger> map = new HashMap<>();
        for (int i = 0; i < numberz.size(); i++) {
            if (map.containsKey(numberz.get(i))) {
                map.get(numberz.get(i)).incrementAndGet();
            } else {
                map.put(numberz.get(i), new AtomicInteger(1));
            }
        }
        List<Map.Entry<Integer, AtomicInteger>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparingInt((Map.Entry<Integer, AtomicInteger> a) -> a.getValue().get()));
        for (Map.Entry<Integer, AtomicInteger> entry : entries) {
            System.out.print("Число " + entry.getKey() + " встречается " + entry.getValue().get() + " раз;\n");
        }
    }

}
