package com.example.demo.patterns.decorator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.decorator.DecoratorPattern.PrioritizedCallable;

public class DecoratorPatternTest {

	@Test
	<T extends Callable<V> & Comparable<T>, V> void decoratorPatternTest() {
		DecoratorPattern.PriorityBufferedSingleThreadedExecutorService<String, PrioritizedCallable<String>> executorService = new DecoratorPattern.PriorityBufferedSingleThreadedExecutorService<>();
		List<Future<String>> results = new ArrayList<>();

		Executors.newSingleThreadExecutor().submit(() -> {
			for (int i = 0; i < 160; i++) {
				Assertions.assertEquals(results.stream().filter(x -> Objects.isNull(x)).count(),
						executorService.getTasksRejected());
				System.out.println("Totally rejected: " + executorService.getTasksRejected() + " tasks;");
				System.out.println("Totally completed: " + executorService.getTasksCompleted() + " tasks;");
				System.out.println("Still running: " + executorService.getTasksInProgress() + " tasks;");
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		for (int i = 0; i < 20; i++) {
			results.add(executorService.submit(new PrioritizedCallable<String>(() -> {
				Thread.currentThread().setName(":MY-TASK-THREAD");
				String resultString = "Thread" + Thread.currentThread().getName() + " has started sleeping at "
						+ LocalTime.now().toString();
				Thread.sleep(1000);
				resultString = resultString + "\nThread" + Thread.currentThread().getName() + " has woke up at "
						+ LocalTime.now().toString();
				return resultString;
			})));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		executorService.shutdown();
		
		results.stream().filter(x -> Objects.nonNull(x)).forEach(future -> {
			String s = "ERROR";
			try {
				s = future.get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("ERROR");
			}
			System.out.println(s);
		});
		
		System.out.println("Сейчас будем ждать в цикле, пока executorService.isTerminated() не вернёт истину");
		
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("executorService.isTerminated() вернул истину, проверяем состояние нашего пула потоков ...");
		Assertions.assertEquals(results.stream().filter(x -> Objects.isNull(x)).count(),
				executorService.getTasksRejected());
		System.out.println("Totally rejected: " + executorService.getTasksRejected() + " tasks;");
		System.out.println("Totally completed: " + executorService.getTasksCompleted() + " tasks;");
		System.out.println("Still running: " + executorService.getTasksInProgress() + " tasks;");
		
		System.out.println("Одна задача все еще может выполняться, даже при том, что executorService.isTerminated() вернул true. Наш executorService.getTasksInProgress() оказался более надежным методом.");
		
		while (executorService.getTasksInProgress() > 0) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Assertions.assertEquals(results.stream().filter(x -> Objects.isNull(x)).count(),
				executorService.getTasksRejected());
		System.out.println("Totally rejected: " + executorService.getTasksRejected() + " tasks;");
		System.out.println("Totally completed: " + executorService.getTasksCompleted() + " tasks;");
		System.out.println("Still running: " + executorService.getTasksInProgress() + " tasks;");
	}
}
