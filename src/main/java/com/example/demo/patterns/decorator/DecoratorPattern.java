package com.example.demo.patterns.decorator;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

// Декоратор (Обёртка) - структурный шаблон проектирования, предназначенный для динамического подключения дополнительного поведения
// к объекту. Шаблон Декоратор предоставляет гибкую альтернативу практике создания подклассов
// с целью расширения функциональности. Декоратор расширяет поведение конкретного компонента, за счет композиции,
// а именно - он содержит в себе ссылку на декорируемого "родителя"
public class DecoratorPattern {

	// Нет необxодимости определять абстрактный класс Decorator, если планируется
	// добавить всего одну обязанность - GoF
	public static class PriorityBufferedSingleThreadedExecutorService<V, T extends Callable<V> & Comparable<T>>
			implements ExecutorService { // Интерфейс декоратора должен соответствовать интерфейсу декорируемого
											// компонента - GoF
		private ExecutorService executorService; // <-- Декорируемый компонент
		private ExecutorService executorServiceForCallbacks;
		private PriorityBlockingQueue<T> taskQueue;

		// В нашем примере шаблона Декоратор, мы будем декорировать имплементацию
		// ExecutorService, порождаемую фабричным методом
		// Executors.newSingleThreadExecutor().
		// Наша цель - добавить стандартному тред пулу ExecutorService такие
		// обязанности, как:
		// 1. подсчет количества выполненных задач
		// 2. отвергнутых задач
		// 3. принятых к выполнению задач
		// 4. выполняемых в данный момент задач
		// 5. ограниченный по размеру буфер-приоритетная очередь задач
		private AtomicInteger completed = new AtomicInteger(0), accepted = new AtomicInteger(0),
				rejected = new AtomicInteger(0);

		public PriorityBufferedSingleThreadedExecutorService() {
			this.executorService = Executors.newSingleThreadExecutor();
			this.executorServiceForCallbacks = Executors.newFixedThreadPool(2); // кто-то должен иметь ссылку на
																				// возвращенный клиенту объект Future,
																				// чтобы мониторить завершение задачи и
																				// увеличить счетчик успешно
																				// завершенных задач, когда это произойдет.
			this.taskQueue = new PriorityBlockingQueue<>();
		}

		public <V> Future<V> submit(Callable<V> task) {

			if (getTasksInProgress() < 3) { // Создаем бутылочное горлышко. Более трех задач наш тред пул не хранит в
											// своей очереди задач на выполнение.
				accepted.incrementAndGet();
				taskQueue.offer((T) task);
				Future<V> resultFuture = (Future<V>) executorService.submit(taskQueue.poll());

				executorServiceForCallbacks.execute(() -> {
					while (!resultFuture.isDone()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							throw new RuntimeException("Interrupted main thread");
						}
					}
					completed.incrementAndGet();
				});
				return resultFuture;
			} else {
				rejected.incrementAndGet();
				return null;
			}
		}

		public int getTasksCompleted() {
			return completed.get();
		}

		public int getTasksAccepted() {
			return accepted.get();
		}

		public int getTasksRejected() {
			return rejected.get();
		}

		public int getTasksInProgress() {
			return accepted.get() - completed.get();
		}

		@Override
		public void execute(Runnable command) {
			executorService.execute(command);
		}

		@Override
		public void shutdown() {
			executorService.shutdown();
		}

		@Override
		public List<Runnable> shutdownNow() {
			return executorService.shutdownNow();
		}

		@Override
		public boolean isShutdown() {
			return executorService.isShutdown();
		}

		@Override
		public boolean isTerminated() {
			return executorService.isTerminated();
		}

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			return executorService.awaitTermination(timeout, unit);
		}

		@Override
		public <T> Future<T> submit(Runnable task, T result) {
			return executorService.submit(task, result);
		}

		@Override
		public Future<?> submit(Runnable task) {
			return executorService.submit(task);
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
			return executorService.invokeAll(tasks);
		}

		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException {
			return executorService.invokeAll(tasks, timeout, unit);
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
				throws InterruptedException, ExecutionException {
			return executorService.invokeAny(tasks);
		}

		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException, TimeoutException {
			return executorService.invokeAny(tasks, timeout, unit);
		}
	}

	// Еще один пример декоратора (обёртки) - расширение произвольной имплементации объекта Callable 
	// функциональностью сравнимости (интерфейс Comparable) экземпляров по времени создания объекатов-задач.
	public static class PrioritizedCallable<T> implements Callable<T>, Comparable<PrioritizedCallable<T>> {

		Callable<T> callable; // <-- Декорируемый объект, функциональность которого расширяется применением паттерна Декоратор
		Instant instantOfCreation;

		public PrioritizedCallable(Callable<T> callable) {
			super();
			this.callable = callable;
			this.instantOfCreation = Instant.now(); // момент создания экземпляра нашей обёрточной имплементации Callable
		}

		@Override
		public T call() throws Exception {
			return this.callable.call();
		}

		@Override
		public int compareTo(PrioritizedCallable<T> o) {
			return Comparator.<PrioritizedCallable<T>>comparingLong(prioritizedCallable -> Duration
					.between(prioritizedCallable.instantOfCreation, Instant.now()).toNanos()).compare(this, o);
		}

	}
}