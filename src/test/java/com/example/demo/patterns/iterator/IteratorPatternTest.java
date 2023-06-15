package com.example.demo.patterns.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.iterator.ItaratorPattern.ConcreteAggregate;
import com.example.demo.patterns.iterator.ItaratorPattern.Iterator;

public class IteratorPatternTest {
	
	@Test
	void iteratorTest() {

		ConcreteAggregate<String> aggregate = new ConcreteAggregate<>(new String[]{"Kaboom", "Kaboom", "Kaboom", "Kaboom", "Kaboom"});
		Iterator<String> i = aggregate.createIterator();

		int counter = 0;
		while (i.hasNext()) {
			Assertions.assertEquals("Kaboom", i.next());
			counter++;
		}
		Assertions.assertEquals(5, counter);

	}

}
