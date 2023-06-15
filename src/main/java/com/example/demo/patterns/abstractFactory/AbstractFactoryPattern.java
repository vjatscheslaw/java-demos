package com.example.demo.patterns.abstractFactory;

// Абстрактная Фабрика (Kit, Инструментарий) - порождающий шаблон; предоставляет интерфейс для создания семейств взаимосвязанных или взаимозависимых объектов,
// не определяя их конкретных классов. 
// Шаблон реализуется созданием двух иерархий типов - иерархия типов фабрик и иерархия типов продуктов. Абстрактный класс DeviceFactory
// представляет собой Абстрактную Фабрику - интерфейс для создания компонентов системы разных семейств
// (например, для оконного интерфейса он может создавать окна, кнопки и подсказки, что составляет семейство продуктов фабрики).
public class AbstractFactoryPattern {

	public static enum Localization {
		RU, EN
	}

	// В нашем примере, у нашей Абстрактной Фабрики будут две имплементации, каждая 
	// из которых по-своему будет создавать объекты семейства продуктов своей Абстрактной фабрики.
	public static DeviceFactory getDeviceFactoryByCountryCode(Localization countryCode) {
		return switch (countryCode) {
		case RU -> new RuDeviceFactory();
		case EN -> new EnDeviceFactory();
		default -> null;
		};
	}
	
	// В нашем примере, в семействе продуктов Абстрактной фабрики будет 3 члена - мышь, клавиатура, тачпад.

	// Член семейства продуктов фабрики
	static interface Mouse {
		void click();

		void dblclick();

		void scroll(int direction);
		
		String getDescription();
	}

	// Член семейства продуктов фабрики
	static interface Keyboard {
		void type();
		String getDescription();
	}

	// Член семейства продуктов фабрики
	static interface Touchpad {
		void track(int deltaX, int deltaY);
		String getDescription();
	}

	// Абстрактная фабрика
	// передоверяет создание объектов-продуктов своим подклассам - GoF
	static interface DeviceFactory {
		Mouse produceMouse(); // <-- Фабричный метод

		Keyboard produceKeyboard(); // <-- Фабричный метод

		Touchpad produceTouchpad(); // <-- Фабричный метод
	}

	// конкретная реализация продукта семейства
	static class EnMouse implements Mouse {
		@Override
		public void click() {
			System.out.println("Mouse click");
		}

		@Override
		public void dblclick() {
			System.out.println("Mouse doubleclick");
		}

		@Override
		public void scroll(int direction) {
			if (direction > 0)
				System.out.println("Scroll up");
			else if (direction < 0)
				System.out.println("Scroll down");
			else
				System.out.println("No scrolling");
		}

		@Override
		public String getDescription() {
			return "Mouse";
		}
	}

	// конкретная реализация продукта семейства
	static class EnKeyboard implements Keyboard {

		@Override
		public void type() {
			System.out.print("Typing");
		}

		@Override
		public String getDescription() {
			return "Keyboard";
		}
	}

	// конкретная реализация продукта семейства
	static class EnTouchpad implements Touchpad {

		@Override
		public void track(int deltaX, int deltaY) {
			System.out.println("Moved: x:" + deltaX + " y:" + deltaY);
		}

		@Override
		public String getDescription() {
			return "Touchpad";
		}

	}

	// конкретная реализация продукта семейства
	static class RuMouse implements Mouse {
		@Override
		public void click() {
			System.out.println("Клик мышкой");
		}

		@Override
		public void dblclick() {
			System.out.println("Двойной клик мышкой");
		}

		@Override
		public void scroll(int direction) {
			if (direction > 0)
				System.out.println("Скролл вверх");
			else if (direction < 0)
				System.out.println("Скролл вниз");
			else
				System.out.println("Нет скроллинга");
		}

		@Override
		public String getDescription() {
			return "Мышь";
		}
	}

	// конкретная реализация продукта семейства
	static class RuKeyboard implements Keyboard {

		@Override
		public void type() {
			System.out.print("Нажатие клавиши");
		}

		@Override
		public String getDescription() {
			return "Клавиатура";
		}

	}

	// конкретная реализация продукта семейства
	static class RuTouchpad implements Touchpad {

		@Override
		public void track(int deltaX, int deltaY) {
			System.out.println("Переместился: x:" + deltaX + " y:" + deltaY);
		}

		@Override
		public String getDescription() {
			return "Тачпад";
		}

	}

	// конкретная фабрика
	static class EnDeviceFactory implements DeviceFactory {

		@Override
		public Mouse produceMouse() {
			return new EnMouse();
		}

		@Override
		public Keyboard produceKeyboard() {
			return new EnKeyboard();
		}

		@Override
		public Touchpad produceTouchpad() {
			return new EnTouchpad();
		}
	}
	
	// конкретная фабрика
	static class RuDeviceFactory implements DeviceFactory {

		@Override
		public Mouse produceMouse() {
			return new RuMouse();
		}

		@Override
		public Keyboard produceKeyboard() {
			return new RuKeyboard();
		}

		@Override
		public Touchpad produceTouchpad() {
			return new RuTouchpad();
		}
	}
}
