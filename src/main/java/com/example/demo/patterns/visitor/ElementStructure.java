package com.example.demo.patterns.visitor;

import java.util.Arrays;

public class ElementStructure {

	static final class BodyElement extends Part {
		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}

	static final class EngineElement extends Part {
		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}

	static final class WheelElement extends Part {
		private String name;

		public WheelElement(String name) {
			this.name = name;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	/**
	 * Это класс-компоновщик, который позволяет обращаться как к листовому элементу,
	 * так и к ветви(коллекции, совокупности), используя один и тот же вызов, что
	 * позволяет осуществлять Посетителю обход древовидных структур
	 */
	static final class CarElement extends Part {
		private Part[] elements;

		public CarElement() {
			this.elements = new Part[] { new WheelElement("front left"), new WheelElement("front right"),
					new WheelElement("back left"), new WheelElement("back right"), new BodyElement(),
					new EngineElement() };
		}

		@Override
		public void accept(Visitor visitor) {
			for (Part e : elements) {
				e.accept(visitor);
			}
			visitor.visit(this);
		}

		@Override
		public boolean isOk() {
			if (Arrays.stream(elements).allMatch(Part::isOk) && this.condition) return true;
			return false;
		}
	}

}
