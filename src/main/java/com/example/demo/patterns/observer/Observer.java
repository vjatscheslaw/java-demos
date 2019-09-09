package com.example.demo.patterns.observer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Observer поведенческий шаблон проектирования. Также известен как «подчинённые» (Dependents).
// Реализует у класса механизм, который позволяет объекту этого класса получать
// оповещения об изменении состояния других объектов и тем самым наблюдать за ними.
public class Observer {
    public static void main(String args[]) {

        MeteoStation station = new MeteoStation();
        //station.addObserver(new FileObzerver());
        station.addObserver(new ConsoleObzerver());
        station.addObserver(new ConsoleObzerver());
        station.addObserver(new ConsoleObzerver());
        station.setMeasurements(30, 140);
        station.setMeasurements(31, 141);

    }

}

interface Observed {
    void addObserver(Obzerver obzerver);
    void removeObserver(Obzerver obzerver);
    void notifyObservers();
}

class MeteoStation implements Observed {

    int temperatire;
    int pressure;
    List<Obzerver> observers = new ArrayList<>();

    void setMeasurements(int t, int pressure) {
        this.temperatire = t;
        this.pressure = pressure;
        notifyObservers();
    }

    @Override
    public void addObserver(Obzerver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Obzerver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Obzerver o : observers) {
            o.handleEvent(temperatire, pressure);
        }
    }
}

interface Obzerver {
    void handleEvent(int temp, int press);
}

class ConsoleObzerver implements Obzerver {

    @Override
    public void handleEvent(int temp, int press) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(System.out))) {
            writer.write("Weather has changed: Temp:" + temp + " Press: " + press + "\n" );
            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

class FileObzerver implements Obzerver {

    @Override
    public void handleEvent(int temp, int press) {
        try (PrintWriter pw = new PrintWriter(
                new FileOutputStream(
                        Files.createFile(Paths.get("/home/vaclav/Desktop/meteo.txt"))
                                .toFile())))
        {
            pw.println("Weather has changed: " + temp + " " + press);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}