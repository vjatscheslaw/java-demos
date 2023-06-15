package com.example.demo.patterns.iterator;

// Итератор (Курсор) - поведенческий шаблон проектирования. 
// Предоставляет способ последовательного доступа ко всем элементам составного объекта (агрегата),
// не раскрывая его внутреннего представления.
public class ItaratorPattern {

	public static interface Iterator<T> {
		public boolean hasNext();

		public T next();
	}

	public static interface Aggregate<T> {
		Iterator<T> createIterator(); // createIterator() - это пример использования шаблона Фабричный Метод.
										// Применение Фабричного Метода приводит к появлению двух иерархий классов - одной для
										// объектов-агрегатов, другой - для объектов-итераторов. - GoF

		T[] getElements();

		int size();
	}

	static class ConcreteAggregate<T> implements Aggregate<T> {
		private T[] elements;

		public ConcreteAggregate(T[] elements) {
			super();
			this.elements = elements;
		}

		public T[] getElements() {
			return elements;
		}

		public int size() {
			return elements.length;
		}

		@Override
		public Iterator<T> createIterator() {
			return new ConcreteIterator<T>(this);
		}

		// В шаблоне Итератор, объект-агрегат должен сам создавать для себя
		// подходящий объект-итератор. Поэтому, нужно, чтобы было невозможно
		// инстанцировать конкретный класс объекта-итератора.
		private static class ConcreteIterator<T> implements Iterator<T> {
			int index = 0;
			Aggregate<T> aggregate;

			ConcreteIterator(Aggregate<T> aggregate) {
				super();
				this.aggregate = aggregate;
			}

			@Override
			public boolean hasNext() {
				return index < aggregate.size();
			}

			@Override
			public T next() {
				return aggregate.getElements()[index++];
			}
		}
	}
}
