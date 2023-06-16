package com.example.demo.patterns.adapter;

// Адаптер (aka Wrapper, обёртка. Ага, прямо как в Декораторе) - структурный шаблон проектирования, который преобразует интерфейс одного класса
// в интерфейс другого, который ожидают клиенты. Адаптер обеспечивает совместную работу классов с несовместимыми интерфейсами,
// которая без него была бы невозможна. - GoF
public class AdapterPattern {

	// В нашем примере, у нас будет наш собственный класс с API интерфейса
	// VectorGraphicsInterface (для этого API написан наш клиентский код, его мы тоже не хотим менять)
	// и класс воображаемой инструментальной библиотеки, в
	// которой есть класс RasterGraphics, который мы,
	// разумеется, не можем изменить (вообразим, что это JAR-файл).
	static interface ModernInterface {
		String printEzh(); // так называются эти функции в новом API
		String printDezh(); // так называются эти функции в новом API
	}

	// класс из инструментальной библиотеки с устаревшим API, который нам
	// нужно использовать (делегация вызовов) из классов с новым API
	// (VectorGraphicsInterface)
	static class OutdatedLibraryKit {
		String printSymbolEzh() { // так называются эти функции в старом API нашей воображаемой инструментальной библиотеки
			return new String(new char[]{'\u0293'});
		}

		String printSymbolDezh() { // так называются эти функции в старом API нашей воображаемой инструментальной библиотеки
			return new String(new char[]{'\u02A4'});
		}
	}

	// Адаптация посредством наследования
	static class ModernLibraryKit extends OutdatedLibraryKit implements ModernInterface {

		@Override
		public String printEzh() {
			return printSymbolEzh();
		}

		@Override
		public String printDezh() {
			return printSymbolDezh();
		}
	}

	// Адаптация посредством композиции
	static class ModernLibraryKit2 implements ModernInterface {

		OutdatedLibraryKit rg = new OutdatedLibraryKit(); // Композиция это такой вид агрегации, при котором жизненный цикл
													// агрегируемого объекта ограничен длительностью жизненного цикла
													// объекта-хозяина (агрегата)

		@Override
		public String printEzh() {
			return rg.printSymbolEzh();
		}

		@Override
		public String printDezh() {
			return rg.printSymbolDezh();
		}
	}
}
