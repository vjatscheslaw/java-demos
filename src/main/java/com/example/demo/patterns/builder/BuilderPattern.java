package com.example.demo.patterns.builder;

// Порождающий шаблон проектирования. Отделяет конструирование сложного объекта от его представления
// так, что в результате одного и того же процесса конструирования могут получаться разные представления.
public class BuilderPattern {

    public static void main(String[] args) {

        CarBuilder builder = new CarBuilder();
        System.out.println(builder.buildMarke("BMW").buildSpeed(320).buildTransmission(Transmission.AUTO).build());

    }
}

class Car {

    int speed;
    Transmission t;
    String marke;

    public Car() {
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Transmission getT() {
        return t;
    }

    public void setT(Transmission t) {
        this.t = t;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    @Override
    public String toString() {
        return "Car{" +
                "speed=" + speed +
                ", t=" + t +
                ", marke='" + marke + '\'' +
                '}';
    }
}

enum Transmission {
    MANUAL, AUTO
}


class CarBuilder {

    String marke = "Zhighuli";
    Transmission t = Transmission.MANUAL;
    int speed = 120;

    CarBuilder buildMarke(String marke) {
        this.marke = marke;
        return this;
    }

    CarBuilder buildTransmission(Transmission t) {
        this.t = t;
        return this;
    }

    CarBuilder buildSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    Car build() {
        Car car = new Car();
        car.setMarke(marke);
        car.setSpeed(speed);
        car.setSpeed(speed);
        return car;
    }


}
