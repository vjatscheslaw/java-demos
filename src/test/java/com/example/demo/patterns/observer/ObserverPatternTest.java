package com.example.demo.patterns.observer;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.observer.ObserverPattern.ArchitectureTelegramChannel;
import com.example.demo.patterns.observer.ObserverPattern.CatsTelegramChannel;
import com.example.demo.patterns.observer.ObserverPattern.CoffeeTelegramChannel;
import com.example.demo.patterns.observer.ObserverPattern.LiteSubscriber;
import com.example.demo.patterns.observer.ObserverPattern.ProSubscriber;
import com.example.demo.patterns.observer.ObserverPattern.Publisher;
import com.example.demo.patterns.observer.ObserverPattern.Subscriber;
import com.example.demo.patterns.observer.ObserverPattern.TravelTelegramChannel;

public class ObserverPatternTest {

	@Test
	void testObserverPattern() {
		Publisher archies = new ArchitectureTelegramChannel();
		Publisher pussies = new CatsTelegramChannel();
		Publisher baristas = new CoffeeTelegramChannel();
		Publisher nomads = new TravelTelegramChannel();

		Subscriber observer1 = new LiteSubscriber();
		Subscriber observer2 = new ProSubscriber();
		Subscriber observer3 = new LiteSubscriber();
		Subscriber observer4 = new ProSubscriber();
		Subscriber observer5 = new LiteSubscriber();

		observer1.subscribe(archies);

		observer2.subscribe(archies);
		observer2.subscribe(nomads);

		observer3.subscribe(pussies);

		observer4.subscribe(pussies);
		observer4.subscribe(nomads);

		observer5.subscribe(baristas);
		observer5.subscribe(nomads);

		AtomicInteger counter = new AtomicInteger(0);
		try {
			while (counter.incrementAndGet() < 3) {
				archies.publish("Вот это здание! [" + counter.get() + "]");
				pussies.publish("мяу мяу [" + counter.get() + "]");
				baristas.publish("ARABICA 110% [" + counter.get() + "]");
				nomads.publish("Купи недвижимость в Дубае! [" + counter.get() + "]");
				archies.publish("Небоскрёб в стиле постмодерн [" + counter.get() + "]");
				pussies.publish("кусь царап [" + counter.get() + "]");
				Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter.set(10);
		
		observer2.unsubscribe(archies);
		observer5.unsubscribe(nomads);
		observer4.unsubscribe(pussies);
		observer2.unsubscribe(nomads);
		
		try {
			while (counter.incrementAndGet() < 13) {
				archies.publish("Вот это здание! [" + counter.get() + "]");
				pussies.publish("мяу мяу [" + counter.get() + "]");
				baristas.publish("ARABICA 110% [" + counter.get() + "]");
				nomads.publish("Купи недвижимость в Дубае! [" + counter.get() + "]");
				archies.publish("Небоскрёб в стиле постмодерн [" + counter.get() + "]");
				pussies.publish("кусь царап [" + counter.get() + "]");
				Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter.set(20);
		
		observer1.unsubscribe(archies);
		observer3.unsubscribe(pussies);
		observer4.unsubscribe(nomads);
		observer5.unsubscribe(baristas);
		observer1.subscribe(baristas);
		
		try {
			while (counter.incrementAndGet() < 23) {
				archies.publish("Вот это здание! [" + counter.get() + "]");
				pussies.publish("мяу мяу [" + counter.get() + "]");
				baristas.publish("ARABICA 110% [" + counter.get() + "]");
				nomads.publish("Купи недвижимость в Дубае! [" + counter.get() + "]");
				archies.publish("Небоскрёб в стиле постмодерн [" + counter.get() + "]");
				pussies.publish("кусь царап [" + counter.get() + "]");
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assertions.assertTrue(observer1.messageBus.isEmpty());
		Assertions.assertTrue(observer2.messageBus.isEmpty());
		Assertions.assertTrue(observer3.messageBus.isEmpty());
		Assertions.assertTrue(observer4.messageBus.isEmpty());
		Assertions.assertTrue(observer5.messageBus.isEmpty());
	}
}
