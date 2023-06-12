package com.example.demo.patterns.templateMethod;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.templateMethod.TemplateMethodPattern.*;

public class TemplateMethodPatternTest {

	@Test
	void testTemplateMethod() {

		LocalTime time = LocalTime.now();
		C tMethod = null;
		if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0)))
			tMethod = new Morning();
		else if (time.isAfter(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(18, 0)))
			tMethod = new Day();
		else if (time.isAfter(LocalTime.of(18, 0)) && time.isBefore(LocalTime.of(22, 0)))
			tMethod = new Evening();
		else
			tMethod = new Night();
		Assertions.assertEquals("Триста тридцать три", tMethod.templteMethod());
		tMethod = new A();
		Assertions.assertNotEquals("Триста тридцать три", tMethod.templteMethod());
		Assertions.assertEquals("333", tMethod.templteMethod());
	}

}
