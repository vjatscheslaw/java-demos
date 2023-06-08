package com.example.demo.patterns.visitor;

import org.junit.jupiter.api.Assertions;

import com.example.demo.patterns.visitor.ElementStructure.CarElement;
import com.example.demo.patterns.visitor.VisitorStructure.HooliganVisitor;
import com.example.demo.patterns.visitor.VisitorStructure.MechanicVisitor;

public class VisitorPatternRunner {

	public static void main(String... args) {

		Visitor hooligan = new HooliganVisitor();
		Visitor mechanic = new MechanicVisitor();
		var car = new CarElement();
		Assertions.assertTrue(car.isOk());
		car.accept(hooligan);
		Assertions.assertFalse(car.isOk());
		car.accept(mechanic);
		Assertions.assertTrue(car.isOk());

	}
}
