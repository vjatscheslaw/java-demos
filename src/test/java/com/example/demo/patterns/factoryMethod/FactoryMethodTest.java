package com.example.demo.patterns.factoryMethod;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FactoryMethodTest {
	
	@Test
	void testFactoryMethod() {
		FactoryMethod.ISOWatchFactory isoWatchFactory = new FactoryMethod.ISOWatchFactory();
		FactoryMethod.LocalTimeWatchFactory germanWatchFactory = new FactoryMethod.LocalTimeWatchFactory();
		
		System.out.println(isoWatchFactory.createWatch().showTime(LocalDateTime.of(1999, 11, 25, 0, 0, 0)));
		System.out.println(germanWatchFactory.createWatch().showTime(LocalDateTime.of(1999, 11, 25, 0, 0, 0)));
		
		Assertions.assertEquals("1999-11-25T00:00:00", isoWatchFactory.createWatch().showTime(LocalDateTime.of(1999, 11, 25, 0, 0, 0)));
		Assertions.assertEquals("1999 Nov. 25 \\ 00:00:00", germanWatchFactory.createWatch().showTime(LocalDateTime.of(1999, 11, 25, 0, 0, 0)));
	}
}