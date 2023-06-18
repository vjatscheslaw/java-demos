package com.example.demo.patterns.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.command.CommandPattern.*;

public class CommandPatternTest {

	@Test
	void testCommandPattern() {
		
		// Данный тестовый метод представляет собой Клиента в паттерне Команда.
		
		IntNumber receiver = new IntNumber();
		receiver.setValue(5);
		
		Assertions.assertEquals(5, receiver.getValue());
		
		Invoker invoker = new Invoker();
		invoker.invoke(new Multiplication(3, receiver), receiver);
		invoker.invoke(new Subtraction(4, receiver), receiver);
		
		Assertions.assertEquals(11, receiver.getValue());
		Assertions.assertEquals(2, invoker.stackSize());
		
		invoker.undo();
		Assertions.assertEquals(15, receiver.getValue());
		Assertions.assertEquals(1, invoker.stackSize());
		
		invoker.invoke(new Summation(5, receiver), receiver);
		invoker.invoke(new Division(2, receiver), receiver);
		
		Assertions.assertEquals(10, receiver.getValue());
		Assertions.assertEquals(3, invoker.stackSize());
		
		invoker.undo();
		invoker.undo();
		invoker.undo();
	
		Assertions.assertEquals(5, receiver.getValue());
		Assertions.assertEquals(0, invoker.stackSize());
	}
}