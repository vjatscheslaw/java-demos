package com.example.demo.patterns.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Прототип - порождающий шаблон. Задаёт виды создаваемых объектов с помощью экземпляра-прототипа и создаёт
// новые объекты путём копирования этого прототипа. Он позволяет уйти от реализации и позволяет следовать
// принципу «программирование через интерфейсы». В качестве возвращающего типа указывается интерфейс/абстрактный
// класс на вершине иерархии, а классы-наследники могут подставить туда наследника, реализующего этот тип.
public class PrototypePattern {

	record Human(boolean isWoman, int age, String name, Consciousness consciousness) implements Cloneable {
		
		Human(boolean isWoman, int age, String name, Consciousness consciousness) {
			this.isWoman = isWoman;
			this.age = age;
			this.name = name;
			this.consciousness = (Consciousness) consciousness.clone();
		}

		// Самая трудная часть паттерна Прототип - правильная реализация операции Clone. - GoF
		// Этот метод можно было бы улучшить, присваивая клон объекта Consciousness клону Human, 
		// но тогда пришлось бы отказаться от имплементации record
		@Override
		public Human clone() {
			try {
				return (Human) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public String toString() {
			return "Human [" + (isWoman ? "Пол: женский" : "Пол: мужской") + ", age=" + age + ", name=" + name
					+ ", consciousness=" + consciousness + "]";
		}
	}

	// В нашем случае, в "палитре" нашей фабрики будет один инструмент, который она будет клонировать по запросу
	public static class HumanFactory {
		Human human; // <-- Прототип

		public HumanFactory(Human human) {
			setPrototype(human);
		}

		public void setPrototype(Human human) {
			this.human = human;
		}
		
		public Human getPrototype() {
			return this.human;
		}

		// этот метод порождает объект, который занимает иное место в памяти, чем прототип, но который идентичен своему прототипу
		Human getClonedHuman() {
			if (Objects.nonNull(this.human))
				return this.human.clone();
			else {
				System.err.println("Прототип не загружен в фабрику");
				return null;
			}
		}

		Human getCustomHuman(boolean isWoman, int age, String name, Consciousness consciousness) {
			return new Human(isWoman, age, name, consciousness); // <-- не прототип, а никогда не существовавший ранее кастом
		}
	}

	public static class Consciousness implements Cloneable {
		List<String> features = new ArrayList<>();

		public Consciousness(String... features) {
			Arrays.stream(features).forEach(s -> this.features.add(s));
		}

		@Override
		protected Consciousness clone() {
			try {
				Consciousness cTemp = (Consciousness) super.clone();
				cTemp.features  = features.stream().collect(Collectors.toList());
				return cTemp;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.toString());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Consciousness other = (Consciousness) obj;
			if (Objects.equals(features, other.features)) return true;
			return (features.size() == other.features.size()) && features.containsAll(other.features);
		}



		@Override
		public String toString() {
			return "Содержимое сознания ["
					+ features.stream().reduce(new String(), (String s1, String s2) -> s1 + ", " + s2) + "]";
		}
	}
}
