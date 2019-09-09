package com.example.demo.patterns.facade;

//структурный шаблон проектирования, позволяющий скрыть сложность системы путём
// сведения всех возможных внешних вызовов к одному объекту, делегирующему их соответствующим объектам системы.
public class FacadePattern {

    public static void main(String args[]) {

    Computer computer = new Computer(new Power(), new DVDRom(), new HDD());
    computer.copy();
    }
}

class Power {
    void on() {
        System.out.println("PC on");
    }

    void off() {
        System.out.println("PC off");
    }
}

class DVDRom {
    private boolean data = false;
    public boolean hasData() {
        return data;
    }

    void load() {
        data = true;
    }

    void  unload() {
        data = false;
    }
}

class HDD {
    void copyFromDVD(DVDRom dvd) {
        if (dvd.hasData()) {
            System.out.println("copying from disc ... ");
        } else {
            System.out.println("Insert disk, please");
        }
    }
}
//класс-фасад объединяет в себе все компоненты и определяет порядок работы с ними
class Computer {

    private Power power;
    private DVDRom dvdRom;
    private HDD hdd;

    public Computer(Power power, DVDRom dvdRom, HDD hdd) {
        this.power = power;
        this.dvdRom = dvdRom;
        this.hdd = hdd;
    }

    void copy() {

        power.on();
        dvdRom.load();
        hdd.copyFromDVD(dvdRom);

    };

}
