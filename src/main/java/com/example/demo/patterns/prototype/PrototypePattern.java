package com.example.demo.patterns.prototype;

//порождающий шаблон. Задаёт виды создаваемых объектов с помощью экземпляра-прототипа и создаёт
// новые объекты путём копирования этого прототипа. Он позволяет уйти от реализации и позволяет следовать
// принципу «программирование через интерфейсы». В качестве возвращающего типа указывается интерфейс/абстрактный
// класс на вершине иерархии, а классы-наследники могут подставить туда наследника, реализующего этот тип.
public class PrototypePattern {

    public static void main(String[] args) {

        Human original = new Human(17, "Erika");
        System.out.println(original.toString());
        Human copy = (Human) original.copy();
        System.out.println(copy);

        HumanFactory f = new HumanFactory(copy);
        Human h1 = f.makeCopy();
        System.out.println(h1.toString());

        f.setPrototype(new Human(32, "Valeria"));
        System.out.println(f.makeCopy().toString());

    }
}

interface Copyable {
    Object copy();
}

class Human implements Copyable{
    int age;
    String name;

    public Human(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public Object copy() {
        return new Human(age, name);
    }

    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

class HumanFactory {
    Human human;

    public HumanFactory(Human human) {
        setPrototype(human);
    }

    public void setPrototype(Human human) {
        this.human = human;
    }

    Human makeCopy() {
        return (Human) human.copy();
    }
}
