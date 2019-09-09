package com.example.demo.patterns.state;

// поведенческий шаблон проектирования. Используется в тех случаях,
// когда во время выполнения программы объект должен менять
// своё поведение в зависимости от своего состояния.
public class Sostoyanie {

    public static void main(String[] args) {

    Radio radio = new Radio();
    radio.setStation(new Radio7());

    for (int i = 0; i < 10;) {
        radio.play();
        radio.nextStation();
        i++;
    }

}

}

interface Station {
    void play();
}

class Radio7 implements Station {

    @Override
    public void play() {
        System.out.println("Radio 7 .....");
    }
}

class HIT_FM implements Station {

    @Override
    public void play() {
        System.out.println("HIT FM .....");
    }
}

class VestiFM implements Station {

    @Override
    public void play() {
        System.out.println("Vesti FM .....");
    }
}

//Context в зависимости от своего состояния, этот контекст меняет своё поведение.
// Переключение между состояниями происходит внутри контекста, а не внутри состояния
class Radio {
    Station station;

    public void setStation(Station station) {
        this.station = station;
    }

    void nextStation() {
        if (station instanceof Radio7) this.setStation(new VestiFM()); //переключение между конкретными состояниями внутри контекста
        else if (station instanceof VestiFM) this.setStation(new HIT_FM());
        else if (station instanceof HIT_FM) this.setStation(new Radio7());
    }

    void play() {
        station.play();
    }
}