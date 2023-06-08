package com.example.demo.patterns.visitor;

import com.example.demo.patterns.visitor.ElementStructure.BodyElement;
import com.example.demo.patterns.visitor.ElementStructure.CarElement;
import com.example.demo.patterns.visitor.ElementStructure.EngineElement;
import com.example.demo.patterns.visitor.ElementStructure.WheelElement;

// Посетитель это поведенческий шаблон проектирования, описывающий операцию, которая выполняется над каждым
// объектом некоторой структуры. Позволяет определить новую операцию, не изменяя самой структуры.
// При изменении имплементации Посетителя, нет необходимости изменять обслуживаемые им классы.
public class VisitorStructure {

	static final class HooliganVisitor implements Visitor {

		@Override
		public void visit(EngineElement e) {
			e.breakPart();
			System.out.println("Спалил свечи");
		}

		@Override
		public void visit(BodyElement e) {
			e.breakPart();
			System.out.println("Погнул");
		}

		@Override
		public void visit(WheelElement e) {
			e.breakPart();
			System.out.println("Спустил колесо " + e.getName());
		}

		@Override
		public void visit(CarElement e) {
			e.breakPart();
			System.out.println("Покурил в салоне");
		}
	}

	static final class MechanicVisitor implements Visitor {

		@Override
		public void visit(EngineElement e) {
			e.fixPart();
			System.out.println("Заменил свечи");
		}

		@Override
		public void visit(BodyElement e) {
			e.fixPart();
			System.out.println("Отрехтовал");
		}

		@Override
		public void visit(WheelElement e) {
			e.fixPart();
			System.out.println("Подкачал колесо " + e.getName());
		}

		@Override
		public void visit(CarElement e) {
			e.fixPart();
			System.out.println("Проветрил салон");
		}
	}

}
