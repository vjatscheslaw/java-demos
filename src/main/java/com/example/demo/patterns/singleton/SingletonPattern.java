package com.example.demo.patterns.singleton;

import java.util.concurrent.atomic.AtomicInteger;

// Одиночка - порождающий шаблон проектирования. Гарантирует, что у класса
// будет единственный экземпляр, и предоставляет глобальную точку
// доступа к этому экземпляру.
public class SingletonPattern {
	
	// Так как Синглтон это порождающий шаблон, то в данном примере, нас будет интересовать порождение.
	// У каждой имплементации будет поле counter, которое будет хранить количество когда-либо созданных экземпляров.
	// Клиентский код смотрите в тестовом классе.

    //Потоконебезопасный
	static class UnsafeSingleton {
		private static AtomicInteger counter = new AtomicInteger(0);
		private static UnsafeSingleton instance;

		private UnsafeSingleton() {
			counter.incrementAndGet();
		}

		public static UnsafeSingleton getInstance() { // глобальная точка доступа - публичный статически метод
			if (instance == null)
				instance = new UnsafeSingleton();
			return instance;
		}

		public static int getCount() {
			return counter.get();
		}
	}

    //потокобезопасный, но с EAGER инициализацией
	public static class SafeEagerSingleton {
		private static AtomicInteger counter = new AtomicInteger(0);
		private static SafeEagerSingleton instance = new SafeEagerSingleton();

		private SafeEagerSingleton() {
			counter.incrementAndGet();
		}

		public static SafeEagerSingleton getInstance() { // глобальная точка доступа - публичный статически метод
			return instance;
		}

		public static int getCount() {
			return counter.get();
		}
	}

	// потокобезопасный, с LAZY инициализацией, блокировка на мониторе-объекте
	// SafeLazySlowSingleton перед входом в synchronized
	// требуется каждый раз при вызове метода getInstance().
	public static class SafeLazySlowSingleton {
		private static AtomicInteger counter = new AtomicInteger(0);
		private static SafeLazySlowSingleton instance = null;

		private SafeLazySlowSingleton() {
			counter.incrementAndGet();
		}

		public static synchronized SafeLazySlowSingleton getInstance() { // глобальная точка доступа - публичный статически метод
			if (instance == null)
				instance = new SafeLazySlowSingleton();
			return instance;
		}

		public static int getCount() {
			return counter.get();
		}
	}

	// потокобезопасный, с LAZY инициализацией, блокировка на мониторе-объекте
	// SafeLazyOptimizedSingleton перед
	// входом в synchronized требуется единственный раз.
	public static class SafeLazyOptimizedSingleton {
		private static AtomicInteger counter = new AtomicInteger(0);
		private static volatile SafeLazyOptimizedSingleton instance = null;

		private SafeLazyOptimizedSingleton() {
			counter.incrementAndGet();
		}

		public static SafeLazyOptimizedSingleton getInstance() { // глобальная точка доступа - публичный статически метод
			if (instance == null)
				synchronized (SafeLazyOptimizedSingleton.class) {
					if (instance == null)
						instance = new SafeLazyOptimizedSingleton();
				}
			return instance;
		}

		public static int getCount() {
			return counter.get();
		}
	}

	// потокобезопасный, с LAZY инициализацией, без блока synchronized
	public enum EnumSingleton {

		ENUM_SINGLETON();  // глобальная точка доступа

		private AtomicInteger counter = new AtomicInteger(0);

		private EnumSingleton() {
			counter.incrementAndGet();
		}

		public int getCount() {
			return counter.get();
		}
	}
}
