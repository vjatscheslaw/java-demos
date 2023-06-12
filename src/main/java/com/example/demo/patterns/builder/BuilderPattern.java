package com.example.demo.patterns.builder;

// Строитель - порождающий шаблон проектирования. Отделяет конструирование сложного объекта от его представления
// так, что в результате одного и того же процесса конструирования могут получаться разные представления - GoF
public class BuilderPattern {

	// Абстрактное представление
	abstract static class Vehicle {
		private VehicleType vehicle;
		private Category category;
		private String manufacturer;
		private Transmission transmission;
		private int maxSpeed;

		Vehicle(VehicleType vehicle, Category category, String manufacturer, Transmission transmission, int maxSpeed) {
			this.setCategory(category);
			this.setManufacturer(manufacturer);
			this.setMaxSpeed(maxSpeed);
			this.setTransmission(transmission);
			this.setVehicle(vehicle);
		}

		public VehicleType getVehicle() {
			return vehicle;
		}

		public void setVehicle(VehicleType vehicle) {
			this.vehicle = vehicle;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public Transmission getTransmission() {
			return transmission;
		}

		public void setTransmission(Transmission transmission) {
			this.transmission = transmission;
		}

		public int getMaxSpeed() {
			return maxSpeed;
		}

		public void setMaxSpeed(int maxSpeed) {
			this.maxSpeed = maxSpeed;
		}

		@Override
		public String toString() {
			return "" + vehicle + "{" + "max speed=" + maxSpeed + ", transmission type = " + transmission
					+ ", manufacturer='" + manufacturer + "', category" + category + "}";
		}

	}

	// Конкретное представление
	static class Car extends Vehicle {
		public Car(VehicleType vehicle, Category category, String manufacturer, Transmission transmission,
				int maxSpeed) {
			super(vehicle, category, manufacturer, transmission, maxSpeed);
		}
	}

	// Конкретное представление
	static class Truck extends Vehicle {
		public Truck(VehicleType vehicle, Category category, String manufacturer, Transmission transmission,
				int maxSpeed) {
			super(vehicle, category, manufacturer, transmission, maxSpeed);
		}
	}

	// Конкретное представление
	static class Motorbike extends Vehicle {
		public Motorbike(VehicleType vehicle, Category category, String manufacturer, Transmission transmission,
				int maxSpeed) {
			super(vehicle, category, manufacturer, transmission, maxSpeed);
		}
	}

	enum Transmission {
		MANUAL, AUTOMATIC
	}

	enum Category {
		A, B, C
	}

	enum VehicleType {
		CAR, TRUCK, MOTORBIKE
	}

	// Абстрактный строитель
	static abstract class VehicleBuilder {
		abstract Vehicle build();
	}

	// Конкретный строитель
	static class CarBuilder extends VehicleBuilder {

		VehicleType vehicle = VehicleType.CAR;
		Category category = Category.B;
		String manufacturer = "LADA";
		Transmission transmission = Transmission.MANUAL;
		int maxSpeed = 120;

		CarBuilder manufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		CarBuilder transmission(Transmission transmission) {
			this.transmission = transmission;
			return this;
		}

		CarBuilder maxSpeed(int speed) {
			this.maxSpeed = speed;
			return this;
		}

		@Override
		Vehicle build() {
			return new Car(this.vehicle, this.category, this.manufacturer, this.transmission, this.maxSpeed);
		}
	}

	// Конкретный строитель
	static class TruckBuilder extends VehicleBuilder {

		VehicleType vehicle = VehicleType.TRUCK;
		Category category = Category.C;
		String manufacturer = "GAZelle";
		Transmission transmission = Transmission.MANUAL;
		int maxSpeed = 110;

		TruckBuilder manufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		TruckBuilder transmission(Transmission transmission) {
			this.transmission = transmission;
			return this;
		}

		TruckBuilder maxSpeed(int speed) {
			this.maxSpeed = speed;
			return this;
		}

		@Override
		Vehicle build() {
			return new Truck(this.vehicle, this.category, this.manufacturer, this.transmission, this.maxSpeed);
		}
	}

	// Конкретный строитель
	static class MotorbikeBuilder extends VehicleBuilder {

		VehicleType vehicle = VehicleType.MOTORBIKE;
		Category category = Category.A;
		String manufacturer = "BMW";
		Transmission transmission = Transmission.AUTOMATIC;
		int maxSpeed = 220;

		MotorbikeBuilder manufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		MotorbikeBuilder transmission(Transmission transmission) {
			this.transmission = transmission;
			return this;
		}

		MotorbikeBuilder maxSpeed(int speed) {
			this.maxSpeed = speed;
			return this;
		}

		@Override
		Vehicle build() {
			return new Motorbike(this.vehicle, this.category, this.manufacturer, this.transmission, this.maxSpeed);
		}
	}
}
