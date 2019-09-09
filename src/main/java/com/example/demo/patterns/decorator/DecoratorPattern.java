package com.example.demo.patterns.decorator;

// структурный шаблон проектирования, предназначенный для динамического подключения дополнительного поведения
// к объекту. Шаблон Декоратор предоставляет гибкую альтернативу практике создания подклассов
// с целью расширения функциональности. РАСШИРЯЕТ ПОВЕДЕНИЕ КОНКРЕТНОГО КОМПОНЕНТА ЧЕРЕЗ ССЫЛКУ НА ЕГО РОДИТЕЛЯ
public class DecoratorPattern {
    public static void main(String[] args) {
        IPrinter printer = new LeftQuoteDecorator(new RightQuoteDecorator(new Printer("Hey hey my my")));
        printer.print();
    }

}

interface IPrinter {
    void print();
}

class Printer implements IPrinter {
    String task;

    public Printer(String task) {
        this.task = task;
    }

    @Override
    public void print() {
        System.out.print(task);
    }
}

class LeftQuoteDecorator extends Decorator {


    public LeftQuoteDecorator(IPrinter printer) {
       super(printer);
    }

    @Override
    public void print() {
        System.out.print("\"");
        printer.print();
    }

}

abstract class Decorator implements IPrinter {
    IPrinter printer;

    public Decorator(IPrinter printer) {
        this.printer = printer;
    }
}

class RightQuoteDecorator extends Decorator {

    public RightQuoteDecorator(IPrinter printer) {
        super(printer);
    }

    @Override
    public void print() {
        printer.print();
        System.out.print("\"");
    }
}