package com.example.demo.patterns.bridge;

// структурный шаблон проектирования, используемый в проектировании программного обеспечения
// чтобы «разделять абстракцию и реализацию так, чтобы они могли изменяться независимо». Шаблон
// мост использует инкапсуляцию, агрегирование и может использовать наследование для того,
// чтобы разделить ответственность между классами.
public class Bridge {

    public static void main(String[] args) {
        Car kia = new Coupe(new Kia());
        kia.showDetails();
    }

}

interface Make {
    void setMake();
}

abstract class Car {
    Make make;

    public Car(Make m) {
        make = m;
    }

    public abstract void showType();

    public void showDetails() {
        showType();
        make.setMake();
    }
}

class Sedan extends Car {
    public Sedan(Make m) {
        super(m);
    }

    public void showType() {
        System.out.println("Sedan");
    }
}

class Coupe extends Car {
    public Coupe(Make m) {
        super(m);
    }

    public void showType() {
        System.out.println("Coupe");
    }
}

class Hatchback extends Car {
    public Hatchback(Make m) {
        super(m);
    }

    public void showType() {
        System.out.println("Hatchback");
    }
}

class Kia implements Make {
    public void setMake() {
        System.out.println("Kia");
    }
}

class Nissan implements Make {
    public void setMake() {
        System.out.println("Nissan");
    }
}

class BMW implements Make {
    public void setMake() {
        System.out.println("BMW");
    }
}


