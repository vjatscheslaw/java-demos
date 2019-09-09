package com.example.demo.patterns.visitor;

//поведенческий шаблон проектирования, описывающий операцию, которая выполняется над
// объектами других классов. При изменении visitor нет необходимости изменять обслуживаемые классы.
public class VisitorPattern {

    public static void main(String[] args) {

        Visitor hooligan = new HooliganVisitor();
        Visitor mechanic = new MechanicVisitor();
        Element engine = new EngineElement();
        Element body = new BodyElement();
        Element car = new CarElement();
        car.accept(mechanic);

    }

}

interface Visitor {
    void visit(EngineElement ee);
    void visit(BodyElement be);
    void visit(HweelElement ee);
    void visit(CarElement be);
}

interface Element {
    void accept(Visitor visitor);
}

class BodyElement implements Element {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class EngineElement implements Element {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class HweelElement implements Element {
    private String name;

    public HweelElement(String name) {
        this.name = name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return this.name;
    }
}

class CarElement implements Element {
    private Element[] elements;

    public CarElement() {
        this.elements = new Element[] {
                new HweelElement("front left"),
        new HweelElement("front right"),
        new HweelElement("back left"),
        new HweelElement("back right"),
                new BodyElement(),
                new EngineElement()
        };
    }

    public void accept(Visitor visitor) {
        for (Element e : elements) {
            e.accept(visitor);
        }
        visitor.visit(this);

    }

}

class HooliganVisitor implements Visitor {

    @Override
    public void visit(EngineElement ee) {
        System.out.println("Спалил свечи");
    }

    @Override
    public void visit(BodyElement be) {
        System.out.println("Погнул");
    }

    @Override
    public void visit(HweelElement ee) {
        System.out.println("Спустил колесо");
    }

    @Override
    public void visit(CarElement be) {
        System.out.println("Покурил в машине");
    }
}

class MechanicVisitor implements Visitor {

    @Override
    public void visit(EngineElement ee) {
        System.out.println("заменил свечи");
    }

    @Override
    public void visit(BodyElement be) {
        System.out.println("отрехтовал");
    }

    @Override
    public void visit(HweelElement ee) {
        System.out.println("Подкачал колесо");
    }

    @Override
    public void visit(CarElement be) {
        System.out.println("Проветрил автомобиль");
    }
}

