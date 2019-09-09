package com.example.demo.patterns.flyweight;

import java.util.HashMap;
import java.util.Map;

// структурный шаблон проектирования, при котором объект, представляющий себя
// как уникальный экземпляр в разных местах программы, по факту не является таковым.
public class Flyweight {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        shapeFactory.getShape("circle").draw(5, 5);
        shapeFactory.getShape("circle").draw(5, 5);
        shapeFactory.getShape("square").draw(5, 5);
        shapeFactory.getShape("circle").draw(5, 5);
        shapeFactory.getShape("point").draw(5, 5);
        shapeFactory.getShape("point").draw(5, 5);

    }

}

//Flyweight
interface Shape {
    void draw(int x, int y);
}

class Point implements Shape {

    @Override
    public void draw(int x, int y) {
        System.out.println("Drawing a point at x:" + x + " y:" + y);
    }
}

class Square implements Shape {
    int side = 10;
    @Override
    public void draw(int x, int y) {
        System.out.println("Drawing a square at x:" + x + " y:" + y + " with side of " + side);
    }
}

class Circle implements Shape {
    int radius = 5;
    @Override
    public void draw(int x, int y) {
        System.out.println("Drawing a circle at x:" + x + " y:" + y + " with radius of " + radius);
    }
}

//весь цимес здесь
class ShapeFactory {
    private static final Map<String, Shape> shapes = new HashMap<>();
    public Shape getShape(String shapeName) {
        Shape shape = shapes.get(shapeName); //повторное использование ранее созданного конкретного объекта
        if (shape==null) {
            switch (shapeName) {
                case "circle":
                    shape = new Circle();
                    break;
                case "square":
                    shape = new Square();
                    break;
                case "point":
                    shape = new Point();
                    break;
            }
            shapes.put(shapeName, shape);
        }
        return shape;
    }
}
