package com.example.demo.patterns.abstractFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.abstractFactory.AbstractFactoryPattern.*;

public class AbstractFactoryPatternTest {

	@Test
	void testAbstractFactory() {

		DeviceFactory enFactory = AbstractFactoryPattern.getDeviceFactoryByCountryCode(Localization.EN);
		DeviceFactory ruFactory = AbstractFactoryPattern.getDeviceFactoryByCountryCode(Localization.RU);

		// Семейство объектов для русскоязычных пользователей
		Touchpad localizedRussianTouchpad = ruFactory.produceTouchpad();
		Mouse localizedRussianMouse = ruFactory.produceMouse();
		Keyboard localizedRussianKeyboard = ruFactory.produceKeyboard();

		// Семейство объектов для англоязычных пользователей
		Touchpad localizedEnglishTouchpad = enFactory.produceTouchpad();
		Mouse localizedEnglishMouse = enFactory.produceMouse();
		Keyboard localizedEnglishKeyboard = enFactory.produceKeyboard();
		
		System.out.println("Тестируем русифицированные устройства...");
		localizedRussianTouchpad.track(12, 45);
		localizedRussianMouse.click();
		localizedRussianKeyboard.type();
		Assertions.assertEquals("Мышь", localizedRussianMouse.getDescription());
		
		System.out.println("\nТестируем англифицированные устройства...");
		localizedEnglishTouchpad.track(12, 45);
		localizedEnglishMouse.click();
		localizedEnglishKeyboard.type();
		Assertions.assertEquals("Mouse", localizedEnglishMouse.getDescription());

	}
}
