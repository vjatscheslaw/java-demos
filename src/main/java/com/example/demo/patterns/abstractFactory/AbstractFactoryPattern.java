package com.example.demo.patterns.abstractFactory;

//порождающий шаблон  предоставляет интерфейс для создания семейств взаимосвязанных или взаимозависимых объектов,
// не специфицируя их конкретных классов. Шаблон реализуется созданием абстрактного класса Factory,
// который представляет собой интерфейс для создания компонентов системы
// (например, для оконного интерфейса он может создавать окна и кнопки).
// Затем пишутся классы, реализующие этот интерфейс
public class AbstractFactoryPattern {

    public static void main(String[] args) {
        DeviceFactory factory = getDeviceFactoryByCountryCode("en");
        factory.getTouchpad().track(15, 122);
    }

    private static DeviceFactory getDeviceFactoryByCountryCode(String code) {
        switch (code.toUpperCase()) {
            case "RU" : return new RuDeviceFactory();
            case "EN" : return new EnDeviceFactory();
            default: return null;
        }
    }
}

interface Mouse {
    void click();
    void dblclick();
    void scroll(int direction);
}

interface Keyboard {
    void print();
    void println();
}

interface Touchpad {
    void track(int deltaX, int deltaY);
}

interface DeviceFactory {
    Mouse getMouse();
    Keyboard getKeyboard();
    Touchpad getTouchpad();
}

class EnMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("Mouse click");
    }

    @Override
    public void dblclick() {
        System.out.println("Mouse doubleclick");
    }

    @Override
    public void scroll(int direction) {
        if (direction > 0) System.out.println("Scroll up");
        else if (direction < 0) System.out.println("Scroll down");
        else System.out.println("No scrolling");
    }
}

class EnKeyboard implements Keyboard {

    @Override
    public void print() {
        System.out.print("Print");
    }

    @Override
    public void println() {
        System.out.println("Print line");
    }
}

class EnTouchpad implements Touchpad {

    @Override
    public void track(int deltaX, int deltaY) {
        System.out.println("Moved: x:" + deltaX + " y:" + deltaY);
    }

}

class RuMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("Клик мышкой");
    }

    @Override
    public void dblclick() {
        System.out.println("Двойной клик мышкой");
    }

    @Override
    public void scroll(int direction) {
        if (direction > 0) System.out.println("Скролл вверх");
        else if (direction < 0) System.out.println("Скролл вниз");
        else System.out.println("Нет скроллинга");
    }
}

class RuKeyboard implements Keyboard {

    @Override
    public void print() {
        System.out.print("Печать");
    }

    @Override
    public void println() {
        System.out.println("Печать строки");
    }
}

class RuTouchpad implements Touchpad {

    @Override
    public void track(int deltaX, int deltaY) {
        System.out.println("Переместился: x:" + deltaX + " y:" + deltaY);
    }

}

class EnDeviceFactory implements DeviceFactory {

    @Override
    public Mouse getMouse() {
        return new EnMouse();
    }

    @Override
    public Keyboard getKeyboard() {
        return new EnKeyboard();
    }

    @Override
    public Touchpad getTouchpad() {
        return new EnTouchpad();
    }
}


class RuDeviceFactory implements DeviceFactory {

    @Override
    public Mouse getMouse() {
        return new RuMouse();
    }

    @Override
    public Keyboard getKeyboard() {
        return new RuKeyboard();
    }

    @Override
    public Touchpad getTouchpad() {
        return new RuTouchpad();
    }
}
