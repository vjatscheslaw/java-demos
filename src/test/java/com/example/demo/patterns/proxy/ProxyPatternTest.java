package com.example.demo.patterns.proxy;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.proxy.ProxyPattern.*;

public class ProxyPatternTest {

	@Test
	void proxyPatternTest() {

		LocalHomeClockProxy myWallClock = new LocalHomeClockProxy(new AtomicInstituteClock()); // часы настроены на
																								// синхронизацию с
																								// атомными часами в
																								// институте ядерной
																								// физики.
		KitchenClient myKitchen = new KitchenClient(myWallClock); // часы вешаем на кухне (передаём ссылку на
																	// прокси-объект клиенту.) Клиент - человек на кухне

		Assertions.assertAll("Предварительное условие", () -> {
			Assertions.assertFalse(myWallClock.getRequestedTimeCounter() > myWallClock.getUpdatedCacheCounter());
			Assertions.assertEquals(0, myWallClock.getRequestedTimeCounter());
			Assertions.assertEquals(0, myWallClock.getUpdatedCacheCounter());
		});

		Instant startTestInstant = Instant.now();

		ScheduledExecutorService manOnTheKitchen = Executors.newSingleThreadScheduledExecutor();
		manOnTheKitchen.scheduleAtFixedRate(() -> myKitchen.requestTime(), 1000L, 700L, TimeUnit.MILLISECONDS); // человек
																												// на
																												// кухне
																												// несколько
																												// торопится,
																												// так
																												// что
																												// поглядывает
																												// на
																												// часы
																												// каждые
																												// 700
																												// миллисекунд

		while (Duration.between(startTestInstant, Instant.now()).toSeconds() < 10) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		manOnTheKitchen.shutdown();
		while (!manOnTheKitchen.isTerminated()) {
		}

		Assertions.assertTrue(myWallClock.getRequestedTimeCounter() > myWallClock.getUpdatedCacheCounter());
		System.out.println("Запрос точного времени у атомных часов, раз: " + myWallClock.getUpdatedCacheCounter());
		System.out.println("Клиент запросил время, раз: " + myWallClock.getRequestedTimeCounter());

	}
}
