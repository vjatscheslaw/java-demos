package com.example.demo.patterns.visitor;

import com.example.demo.patterns.visitor.ElementStructure.BodyElement;
import com.example.demo.patterns.visitor.ElementStructure.CarElement;
import com.example.demo.patterns.visitor.ElementStructure.EngineElement;
import com.example.demo.patterns.visitor.ElementStructure.WheelElement;

// Интерфейс расширяется как листовым элементом, так и целой ветвью (скомпонованными в коллекцию/массив элементами)
public sealed abstract class Part permits BodyElement, EngineElement, WheelElement, CarElement {
	boolean condition = true;
	public abstract void accept(Visitor visitor);
	public boolean isOk() {
		return this.condition;
	}
	public void breakPart() {
		this.condition = false;
	}
	public void fixPart() {
		this.condition = true;
	}

}
