package com.example.demo.patterns.proxy;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Прокси (aka Суррогат, aka Заместитель) - структурный шаблон проектирования, предоставляющий объект, 
// который является суррогатом другого объекта и контролирует
// доступ к нему, перехватывая все вызовы (выполняет функцию контейнера).
public class ProxyPattern {

	// Интерфейс субъекта
	public interface Clock {
		LocalDateTime getPreciseTime();
	}

	// В паттерне Прокси, заменяемый суррогатом объект называется Реальным
	// Субъектом.
	public static class AtomicInstituteClock implements Clock {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		LocalDateTime preciseTime = LocalDateTime.now();

		public AtomicInstituteClock() {
			executorService.scheduleAtFixedRate(() -> preciseTime = LocalDateTime.now(), 0L, 100L,
					TimeUnit.MILLISECONDS);
		}

		public LocalDateTime getPreciseTime() {
			return this.preciseTime;
		}
	}

	// Это прокси объект, который является суррогатом AtomicInstituteClock и
	// контролирует доступ к нему.
	public static class LocalHomeClockProxy implements Clock {
		Instant lastUpdated;
		Clock preciseClock; // <-- субъект, запросы клиента к которому данный суррогат перехватывает, и
							// решает, что с ними делать - вернуть кешированное значение, или перенаправить
							// запрос к Субъекту.
		LocalDateTime cacheDateTime; // задача нашего кеширующего прокси в том, чтобы разгрузить сеть (атомные часы)
		AtomicInteger requestedTimeCounter = new AtomicInteger(0);
		AtomicInteger updatedCacheCounter = new AtomicInteger(0);

		public LocalHomeClockProxy(Clock preciseClock) {
			this.preciseClock = preciseClock;
			this.cacheDateTime = preciseClock.getPreciseTime();
			lastUpdated = Instant.now();
		}

		public int getRequestedTimeCounter() {
			return requestedTimeCounter.get();
		}

		public int getUpdatedCacheCounter() {
			return updatedCacheCounter.get();
		}

		@Override
		public LocalDateTime getPreciseTime() {
			requestedTimeCounter.incrementAndGet();
			if (Duration.between(lastUpdated, Instant.now()).toMillis() > 1999) {
				this.cacheDateTime = this.preciseClock.getPreciseTime();
				lastUpdated = Instant.now();
				updatedCacheCounter.incrementAndGet();
			}
			return this.cacheDateTime;
		}
	}

	// Клиент - кухня в доме (человек на кухне).
	public static class KitchenClient {
		Clock connectedToInternetKitchenClock; // <-- часы на кухне. Имеют подключение к Интернету.

		public KitchenClient(Clock kitchenClock) {
			this.connectedToInternetKitchenClock = kitchenClock;
		}

		public void requestTime() {
			System.out.println(this.connectedToInternetKitchenClock.getPreciseTime()
					.format(DateTimeFormatter.ofPattern("uuuu MMM dd \\ HH:mm:ss", Locale.US)));
		}
	}

}