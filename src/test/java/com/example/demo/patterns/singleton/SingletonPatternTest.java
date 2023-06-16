package com.example.demo.patterns.singleton;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.singleton.SingletonPattern.EnumSingleton;
import com.example.demo.patterns.singleton.SingletonPattern.SafeEagerSingleton;
import com.example.demo.patterns.singleton.SingletonPattern.SafeLazyOptimizedSingleton;
import com.example.demo.patterns.singleton.SingletonPattern.SafeLazySlowSingleton;
import com.example.demo.patterns.singleton.SingletonPattern.UnsafeSingleton;

public class SingletonPatternTest {

	@Test
	void testSingletonPattern() {
		Map<String, Integer> executionsCounts = new ConcurrentHashMap<>();
		ExecutorService threadPool;

		Instant start;

		threadPool = Executors.newFixedThreadPool(12);
		System.out.println("Потокобезопасная, на перечислении основанная, имплементация");
		start = Instant.now();
		for (int i = 0; i < 90000; i++) {
			threadPool.submit(() -> {
				String instanceName = EnumSingleton.ENUM_SINGLETON.getClass().getName();
				executionsCounts.put(instanceName, executionsCounts.get(instanceName) == null ? 0 : executionsCounts.get(instanceName) + 1);
			});
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Заняло миллисекунд: " + Duration.between(start, Instant.now()).toMillis());
		System.out.println("Создано экземпляров: " + EnumSingleton.ENUM_SINGLETON.getCount());

		threadPool = Executors.newFixedThreadPool(12);
		System.out.println("Потоконебезопасная имплементация");
		start = Instant.now();
		for (int i = 0; i < 90000; i++) {
			threadPool.submit(() -> {
				String instanceName = UnsafeSingleton.getInstance().getClass().getName();
				executionsCounts.put(instanceName, executionsCounts.get(instanceName) == 0 ? 0 : executionsCounts.get(instanceName) + 1);
			});
		}
		
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Заняло миллисекунд: " + Duration.between(start, Instant.now()).toMillis());
		System.out.println("Создано экземпляров: " + UnsafeSingleton.getCount());

		threadPool = Executors.newFixedThreadPool(12);
		System.out.println("Потокобезопасная, с EAGER инициализацией, имплементация");
		start = Instant.now();
		for (int i = 0; i < 90000; i++) {
			threadPool.submit(() -> {
				executionsCounts.put("SafeEagerSingleton", executionsCounts.get("SafeEagerSingleton") == null ? 0 : executionsCounts.get("SafeEagerSingleton") + 1);
			});
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Заняло миллисекунд: " + Duration.between(start, Instant.now()).toMillis());
		System.out.println("Создано экземпляров: " + SafeEagerSingleton.getCount());

		threadPool = Executors.newFixedThreadPool(12);
		System.out.println("Потокобезопасная, с LAZY инициализацией, каждый вызов требующая монитор, имплементация");
		start = Instant.now();
		for (int i = 0; i < 90000; i++) {
			threadPool.submit(() -> {
				String instanceName = SafeLazySlowSingleton.getInstance().getClass().getName();
				executionsCounts.put(instanceName, executionsCounts.get(instanceName) ==  null ? 0 : executionsCounts.get(instanceName) + 1);
			});
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Заняло миллисекунд: " + Duration.between(start, Instant.now()).toMillis());
		System.out.println("Создано экземпляров: " + SafeLazySlowSingleton.getCount());

		threadPool = Executors.newFixedThreadPool(12);
		System.out.println("Потокобезопасная, с LAZY инициализацией, первый вызов требующая монитор, имплементация");
		start = Instant.now();
		for (int i = 0; i < 90000; i++) {
			threadPool.submit(() -> {
				String instanceName = SafeLazyOptimizedSingleton.getInstance().getClass().getName();
				executionsCounts.put(instanceName, executionsCounts.get(instanceName) == null ? 0 : executionsCounts.get(instanceName) + 1);
			});
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Заняло миллисекунд: " + Duration.between(start, Instant.now()).toMillis());
		System.out.println("Создано экземпляров: " + SafeLazyOptimizedSingleton.getCount());

		Assertions.assertEquals(1, SafeLazyOptimizedSingleton.getCount());
		Assertions.assertEquals(1, SafeLazySlowSingleton.getCount());
		Assertions.assertEquals(1, SafeLazyOptimizedSingleton.getCount());
		Assertions.assertEquals(1, EnumSingleton.ENUM_SINGLETON.getCount());
		Assertions.assertEquals(1, UnsafeSingleton.getCount(), 1); // Иногда наш потоконебезопасный синглтон создаёт
																	// более одного экземпляра. Прогоните тест несколько
																	// раз - у меня каждй 4й, примерно, раз, он создаёт
																	// 2 экземпляра. Поэтому, задана дельта.

		Assertions.assertEquals(4, executionsCounts.size());
	}

}
