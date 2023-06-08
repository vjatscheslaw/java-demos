package com.example.demo.patterns.visitor;

import com.example.demo.patterns.visitor.ElementStructure.BodyElement;
import com.example.demo.patterns.visitor.ElementStructure.CarElement;
import com.example.demo.patterns.visitor.ElementStructure.EngineElement;
import com.example.demo.patterns.visitor.ElementStructure.WheelElement;
import com.example.demo.patterns.visitor.VisitorStructure.HooliganVisitor;
import com.example.demo.patterns.visitor.VisitorStructure.MechanicVisitor;

public sealed interface Visitor permits HooliganVisitor, MechanicVisitor {
	void visit(EngineElement ee);

	void visit(BodyElement be);

	void visit(WheelElement ee);

	void visit(CarElement be);
}
