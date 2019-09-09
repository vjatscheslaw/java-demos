package com.example.demo.beans;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Cache {

    private HashMap<String, String> notes;

    private Cache() {
        notes = new HashMap<>();
    }

    public void put(String key, String val) {
        notes.put(key, val);
    }

    public HashMap<String, String> getAll() {
        return notes;
    }

    public String getByKey(String key) {
        return notes.get(key);
    }

    public void post(String val) {
        String key = "";
        int counter = 0;
        while (key.isEmpty() || notes.get(key) != null) {
            key = String.valueOf(Math.round(Math.random()*100000L));
            if (++counter > 10) break;
        }
        notes.put(key, val);
    }



}
