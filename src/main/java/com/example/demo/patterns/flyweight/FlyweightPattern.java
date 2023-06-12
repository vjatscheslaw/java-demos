package com.example.demo.patterns.flyweight;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

// Приспособленец - структурный шаблон проектирования, при котором объект, 
// использующийся в различных контекстах, не является уникальным, что позволяет экономить память.
public class FlyweightPattern {

// Это интерфейс Flyweight
	protected interface Shape {
		void draw(int x, int y); // аргументы x и y представляют собой внешнее состояние приспособленца. Внешнее
									// состояние хранится или вычисляется клиентским кодом, который передаёт его
									// приспособленцу при вызове его операций (метод draw(), в нашем случае)
	}

//ConcreteFlyweight
	public static class Point implements Shape {

		@Override
		public void draw(int x, int y) {
			System.out.println("Drawing a point at x:" + x + " y:" + y);
		}
	}

//ConcreteFlyweight
	public static class Square implements Shape {
		int side = 10; // внутреннее состояние приспособленца.

		@Override
		public void draw(int x, int y) {
			System.out.println("Drawing a square at x:" + x + " y:" + y + " with side of " + side);
		}
	}

//ConcreteFlyweight
	public static class Circle implements Shape {
		int radius = 5; // внутреннее состояние приспособленца

		@Override
		public void draw(int x, int y) {
			System.out.println("Drawing a circle at x:" + x + " y:" + y + " with radius of " + radius);
		}
		
		
	}

//ConcreteFlyweight
	public static class UnsharedConcreteFlyweight implements Shape {
		int radius; // внутреннее состояние приспособленца

		public UnsharedConcreteFlyweight() {
			radius = ThreadLocalRandom.current().nextInt(1, 100);
		}

		@Override
		public void draw(int x, int y) {
			System.out.println("Drawing a circle at x:" + x + " y:" + y + " with radius of " + radius);
		}
	}

	public enum ShapeOption {
		CIRCLE("circle"), SQUARE("square"), POINT("point");

		private String name;

		ShapeOption(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

//FlyweightFactory
	public static class ShapeFactory {
		final Map<String, Shape> shapes = Collections.synchronizedMap(new HashMap<>());

		public Shape getShape(ShapeOption shapeName) {
			if (Objects.isNull(shapeName)) {
				return new UnsharedConcreteFlyweight(); // "Не все подклассы Flyweight обязательно должны быть
				// расшэреными. Интерфейс Flyweight допускает шеринг, но не
				// навязывает его." - GoF
			}
			synchronized (shapes) {
				Shape shape = shapes.get(shapeName.getName()); // повторное использование ранее созданного конкретного объекта
				if (shape == null) {
					switch (shapeName) {
					case CIRCLE:
						shape = new Circle();
						break;
					case SQUARE:
						shape = new Square();
						break;
					case POINT:
						shape = new Point();
						break;
					}
					shapes.put(shapeName.getName(), shape);
				}
				return shape;
			}	
		}
	}
}
