package com.example.demo.patterns.factoryMethod;

import java.util.Date;

// порождающий шаблон проектирования, предоставляющий подклассам (дочерним классам) интерфейс
// для создания экземпляров некоторого класса. В момент создания наследники могут определить,
// какой класс создавать. Иными словами, данный шаблон делегирует создание объектов наследникам
// родительского класса. Это позволяет использовать в коде программы не специфические классы,
// а манипулировать абстрактными объектами на более высоком уровне.
public class FactoryMethod {

    public static void main(String[] args) {
        WatchFactory factory = new RomanWatchFactory();
        factory.createWatch().showTime();
    }

}

interface Watch {
    void showTime();
}

class DigitalWatch implements Watch {
    @Override
    public void showTime() {
        System.out.println(new Date());
    }
}
class RomeWatch implements Watch {
    @Override
    public void showTime() {
        System.out.println("new Date()");
    }
}

interface WatchFactory {
    Watch createWatch(); // <-- это фабричный метод
}

class DigitalWatchFactory implements WatchFactory {

    @Override
    public Watch createWatch() {
        return new DigitalWatch();
    }
}

class RomanWatchFactory implements WatchFactory {

    @Override
    public Watch createWatch() {
        return new RomeWatch();
    }
}