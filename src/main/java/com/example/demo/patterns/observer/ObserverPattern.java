package com.example.demo.patterns.observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Observer поведенческий шаблон проектирования. Также известен как «подчинённые» (Dependents).
// Реализует у класса механизм, который позволяет объекту этого класса получать
// оповещения об изменении состояния других объектов и тем самым наблюдать за ними.
public class ObserverPattern {

	// Subject - располагает информацией о своих наблюдателях. За субъектом (издателем) может
	// следить любое число наблюдателей. - GoF
	public abstract static class Publisher {
		String uniqueIdentifier;
		Set<Subscriber> observers;

		public Publisher() {
			this.observers = new HashSet<>();
			this.uniqueIdentifier = UUID.randomUUID().toString();
		}

		public void addObserver(Subscriber o) {
			observers.add(o);
		}

		public void kickObserver(Subscriber o) {
			o.unsubscribe(this);
		}

		public void publish(String message) {
			this.observers.parallelStream()
					.forEach(observer -> observer.messageBus
							.push(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " : "
									+ this.getClass().getSimpleName() + " сообщает: " + message));
			synchronized (this) {
				this.notifyAll();
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(uniqueIdentifier);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Publisher other = (Publisher) obj;
			return Objects.equals(uniqueIdentifier, other.uniqueIdentifier);
		}
	}

	// Конкретный cубъект (издатель)
	public static class ArchitectureTelegramChannel extends Publisher {}

	// Конкретный cубъект (издатель)
	public static class CoffeeTelegramChannel extends Publisher {}

	// Конкретный cубъект (издатель)
	public static class TravelTelegramChannel extends Publisher {}

	// Конкретный cубъект (издатель)
	public static class CatsTelegramChannel extends Publisher {}

	// Observer - наблюдатель. Определяет интерфейс обновления для объектов, которые
	// должны быть уведомлены об изменении субъекта. - GoF
	public abstract static class Subscriber {
		String uniqueIdentifier;
		Deque<String> messageBus;
		Map<Publisher, Thread> subscriptions;
		ExecutorService personalThreadPool = Executors.newCachedThreadPool();

		public Subscriber() {
			this.uniqueIdentifier = UUID.randomUUID().toString();
			this.messageBus = new ArrayDeque<>();
			this.subscriptions = new HashMap<>();
		}

		public void subscribe(Publisher p) {
			p.observers.add(this);
			this.subscriptions.put(p, new Thread(() -> {
				personalThreadPool.submit(() -> {
					while (!Thread.interrupted() && this.messageBus.isEmpty()) {
						try {
							synchronized (p) {
								p.wait();
							}					
						} catch (InterruptedException e) {
							System.out.println("Подписчик " + uniqueIdentifier + " отписался от " + p.uniqueIdentifier
									+ " в " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
							return;
						}
						while (!this.messageBus.isEmpty()) {
							System.out.println("Подписчик " + uniqueIdentifier + " прочитал: " + messageBus.poll());
						}
					}
					p.observers.remove(this);
				});
			}, "Наблюдатель " + uniqueIdentifier));
			personalThreadPool.submit(subscriptions.get(p));
		}

		public void unsubscribe(Publisher p) {
			p.observers.remove(this);
			synchronized (p) {
				this.subscriptions.get(p).interrupt();
			}	
			this.subscriptions.remove(p);
		}

		@Override
		public int hashCode() {
			return Objects.hash(uniqueIdentifier);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Subscriber other = (Subscriber) obj;
			return Objects.equals(uniqueIdentifier, other.uniqueIdentifier);
		}
	}
	
	// Конкретный наблюдатель
	public static class LiteSubscriber extends Subscriber {

		public LiteSubscriber() {
			super();
			super.uniqueIdentifier = "Lite@" + super.uniqueIdentifier.substring(Math.max(0, super.uniqueIdentifier.length() - 6));
		}
		
	}
	
	// Конкретный наблюдатель
	public static class ProSubscriber extends Subscriber {

		public ProSubscriber() {
			super();
			super.uniqueIdentifier = "Pro@" + super.uniqueIdentifier.substring(Math.max(0, super.uniqueIdentifier.length() - 6));
		}
	}
}
