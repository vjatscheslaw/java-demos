package com.example.demo.patterns.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.builder.BuilderPattern.*;

// Абстрактная фабрика похожа на строитель в том смысле, что может конструировать сложные объекты. Основное различие между ними в том, 
// что строитель делает акцент на пошаговом конструировании объекта, а Абстрактная Фабрика - на создании семейств объектов - GoF  
public class BuilderPatternTest {
	
	// Клиентский код в паттерне Строитель принято называть Распорядителем (Director)
	@Test
	void builderPatternTest() {
		BuilderPattern.CarBuilder builder = new BuilderPattern.CarBuilder();
		BuilderPattern.Vehicle car = builder.manufacturer("Opel").build();
		Assertions.assertEquals(BuilderPattern.Category.B, car.getCategory());
		Assertions.assertEquals("Opel", car.getManufacturer());
		Assertions.assertEquals(120, car.getMaxSpeed());
		Assertions.assertEquals(BuilderPattern.Transmission.MANUAL, car.getTransmission());
		Assertions.assertEquals(BuilderPattern.VehicleType.CAR, car.getVehicle());
		car = new BuilderPattern.CarBuilder().build();
		Assertions.assertEquals("LADA", car.getManufacturer());
		BuilderPattern.TruckBuilder truckBuilder = new BuilderPattern.TruckBuilder();
		car = truckBuilder.build();
		Assertions.assertEquals(BuilderPattern.VehicleType.TRUCK, car.getVehicle());
		Assertions.assertEquals(BuilderPattern.Category.C, car.getCategory());
		BuilderPattern.MotorbikeBuilder motoBuilder = new BuilderPattern.MotorbikeBuilder();
		car = motoBuilder.build();
		Assertions.assertEquals(BuilderPattern.VehicleType.MOTORBIKE, car.getVehicle());
		Assertions.assertEquals(BuilderPattern.Category.A, car.getCategory());
	}

}
