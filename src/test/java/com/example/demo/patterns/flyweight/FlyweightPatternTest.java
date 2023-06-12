package com.example.demo.patterns.flyweight;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.flyweight.FlyweightPattern.ShapeFactory;
import com.example.demo.patterns.flyweight.FlyweightPattern.ShapeOption;

public class FlyweightPatternTest {

	@Test
	void testFlyweightObjectCaching() {
		final ShapeFactory shapeFactory = new ShapeFactory();
		final Set<String> setOfShapes = Collections.synchronizedSet(new HashSet<String>());
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		Instant start = Instant.now();
		while (Duration.between(start, Instant.now()).get(ChronoUnit.NANOS) < 10000000L) {
			threadPool.submit(() -> {
				setOfShapes.add(shapeFactory.getShape(switch (ThreadLocalRandom.current().nextInt(1, 5)) {
				case 1 -> ShapeOption.CIRCLE;
				case 2 -> ShapeOption.SQUARE;
				case 3 -> ShapeOption.POINT;
				default -> null; // в среднем, каждый четвертый вызов не будет прибегать к шаблону Приспособленец.
				}).toString());
			});
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		Pattern circlePatt = Pattern
				.compile("^com\\.example\\.demo\\.patterns\\.flyweight\\.FlyweightPattern\\$Circle@.*$");
		Pattern squarePatt = Pattern
				.compile("^com\\.example\\.demo\\.patterns\\.flyweight\\.FlyweightPattern\\$Square@.*$");
		Pattern pointPatt = Pattern
				.compile("^com\\.example\\.demo\\.patterns\\.flyweight\\.FlyweightPattern\\$Point@.*$");
		Pattern unsharedPatt = Pattern.compile(
				"^com\\.example\\.demo\\.patterns\\.flyweight\\.FlyweightPattern\\$UnsharedConcreteFlyweight@.*$");
		if (threadPool.isTerminated()) {
			Assertions.assertEquals(1, // приспособленец всегда остается одним и тем же объектом
					setOfShapes.stream().filter((String x) -> circlePatt.matcher(x).matches()).count());
			Assertions.assertEquals(1, // приспособленец всегда остается одним и тем же объектом
					setOfShapes.stream().filter((String x) -> squarePatt.matcher(x).matches()).count());
			Assertions.assertEquals(1, // приспособленец всегда остается одним и тем же объектом
					setOfShapes.stream().filter((String x) -> pointPatt.matcher(x).matches()).count());
			Assertions.assertEquals(setOfShapes.size() - 3, // а для этих объектов мы специально не использовали
															// приспособленца, поэтому их много
					setOfShapes.stream().filter((String x) -> unsharedPatt.matcher(x).matches()).count());
			System.out.println(setOfShapes.size() + " of shapes have been generated totally.");
		}
	}
}