package com.example.demo.patterns.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FacadePatternTest {
	
	@Test
	void facadePatternTest() {
		// Фасад предоставляет унифицированный интерфейс вместо набора интерфейсов некоторой подсистемы, 
		// чем упрощает использование подсистемы. В нашем случае, подсистемой является строитель транспортных средств.
		FacadePattern.VehicleGeneratorFacade.VEHICLES_GENERATOR.generateFrenchVehicles()
		.stream()
		.forEach(vehicle -> Assertions.assertEquals("Peugeot", vehicle.getManufacturer()));
	}
}
