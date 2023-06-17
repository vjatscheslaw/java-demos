package com.example.demo.patterns.prototype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.prototype.PrototypePattern.*;

public class PrototypePatternTest {

	@Test
	void prototypePatternTest() {
		HumanFactory hFactory = new HumanFactory(null);
		hFactory.setPrototype(hFactory.getCustomHuman(true, 22, "Кристина", new Consciousness("доброта", "котятки")));

		Human h1 = hFactory.getClonedHuman(); // <-- Клиентский код так порождает объекты при помощи прототипа

		Assertions.assertTrue(hFactory.getPrototype() == hFactory.getPrototype()); // сам по себе, прототип - тот же
																					// самый объект в памяти, пока его
																					// не заменили другим прототипом
		Assertions.assertEquals(true, h1.isWoman());
		Assertions.assertEquals(22, h1.age());
		Assertions.assertEquals("Кристина", h1.name());
		Assertions.assertEquals(new Consciousness("доброта", "котятки"), h1.consciousness());
		Assertions.assertFalse(h1 == hFactory.getPrototype()); // разные объекты в памяти

		Human h2 = hFactory.getClonedHuman(); // <-- Клиентский код так порождает объекты при помощи прототипа

		Assertions.assertEquals(h1.isWoman(), h2.isWoman());
		Assertions.assertEquals(h1.age(), h2.age());
		Assertions.assertEquals(h1.name(), h2.name());
		Assertions.assertEquals(h1.consciousness(), h2.consciousness());
		Assertions.assertTrue(h1.consciousness() == h2.consciousness());
		Assertions.assertTrue(hFactory.getPrototype().consciousness() == h2.consciousness());
		Assertions.assertFalse(h1 == h2); // объекты в памяти разные

		hFactory.setPrototype(hFactory.getCustomHuman(false, 33, "Виталий", hFactory.getPrototype().consciousness()));

		Assertions.assertFalse(hFactory.getPrototype().consciousness() == h2.consciousness());

		hFactory.getPrototype().consciousness().features.add("мужество");
		hFactory.getPrototype().consciousness().features.remove("котятки");

		Human h3 = hFactory.getClonedHuman(); // <-- Клиентский код так порождает объекты при помощи прототипа

		Assertions.assertNotEquals(h3.isWoman(), h2.isWoman());
		Assertions.assertNotEquals(h3.age(), h2.age());
		Assertions.assertNotEquals(h3.name(), h2.name());
		Assertions.assertFalse(h3.consciousness() == h2.consciousness());
		Assertions.assertFalse(h3.consciousness() == h1.consciousness());
		Assertions.assertTrue(h1.consciousness() == h2.consciousness());

		Assertions.assertEquals(new Consciousness("доброта", "мужество"), h3.consciousness());
		Assertions.assertEquals(new Consciousness("мужество", "доброта"), h3.consciousness());

		Assertions.assertEquals(h1.consciousness(), h2.consciousness());
		Assertions.assertNotEquals(h3.consciousness(), h1.consciousness());
		Assertions.assertNotEquals(h3.consciousness(), h2.consciousness());

	}

}
