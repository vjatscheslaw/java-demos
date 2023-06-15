package com.example.demo.patterns.factoryMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

// Фабричный Метод (aka Virtual Constructor) порождающий шаблон проектирования, предоставляющий подклассам интерфейс
// для создания экземпляров некоторого класса. В момент создания, наследники могут определить,
// какой класс создавать. Иными словами, данный шаблон делегирует создание объектов потомкам
// своего класса. Это позволяет использовать в коде программы не специфические классы,
// а манипулировать абстрактными объектами на более высоком уровне.
public class FactoryMethod {

	static interface WatchFactory {
		Watch createWatch(); // <-- Это Фабричный Метод. Он позволяет классу (WatchFactory) делегировать
								// инстанцирование подклассам 
	}

	static interface Watch {
		String showTime(LocalDateTime time);
	}

	static class ISOWatch implements Watch {
		@Override
		public String showTime(LocalDateTime time) {
			return time.format(DateTimeFormatter.ISO_DATE_TIME);
		}
	}

	static class GermanWatch implements Watch {
		@Override
		public String showTime(LocalDateTime time) {
			return time.format(DateTimeFormatter.ofPattern("uuuu MMM dd \\ HH:mm:ss", Locale.GERMANY));
		}
	}

	static class ISOWatchFactory implements WatchFactory {

		@Override
		public Watch createWatch() {
			return new ISOWatch();
		}
	}

	static class LocalTimeWatchFactory implements WatchFactory {

		@Override
		public Watch createWatch() {
			return new GermanWatch();
		}
	}

}