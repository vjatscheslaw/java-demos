package com.example.demo.patterns.command;

import java.util.ArrayDeque;

// Команда (Command aka Action aka Transaction) - поведенческий шаблон проектирования. 
// Инкапсулирует запрос как объект, позволяя тем самым задавать параметры клиентов для обработки соответствующих запросов, 
// ставить запросы в очередь или протоколировать их, а также поддерживать отмену операций. - GoF
public class CommandPattern {

	// Команда
	public static interface Command {
		default void execute(Receiver r) { // Команде передаётся ссылка на конечного получателя
			r.action(this); // что делать в ответ на полученную команду, знает получатель (Receiver).
		}

		Receiver getReceiver();

		Command getOppositeForUndo();
	}

	// Получатель (aka Document, aka Application всмысле приложение команды) -
	// располагает информацией о способах
	// выполнения операций, необходимых для удовлетворения запроса. - GoF
	public static interface Receiver {
		void action(Command command); // метод получателя Команды, который эта Команда будет вызывать,
		// передавая ему своё состояние. Получатель, в свою очередь, будет знать, как
		// применить состояние конкретной Команды.

	}

	// Конкретный получатель команды.
	public static class IntNumber implements Receiver {
		private int value = 0;

		@Override
		public void action(Command command) {
			if (command instanceof Summation sum)
				this.value = this.value + sum.param();
			else if (command instanceof Subtraction subtr)
				this.value = this.value - subtr.param();
			else if (command instanceof Multiplication mult)
				this.value = this.value * mult.param();
			else if (command instanceof Division div)
				this.value = this.value / div.param();
			else
				throw new RuntimeException("Wrong command");
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		};
	}

	// Конкретная команда. Клиент присваивает команде состояние (в нашем примере это
	// параметр param)
	public static record Summation(int param, Receiver r) implements Command {
		@Override
		public Receiver getReceiver() {
			return r;
		}

		@Override
		public Command getOppositeForUndo() {
			return new Subtraction(param, r);
		}
	}

	// Конкретная команда.
	public static record Subtraction(int param, Receiver r) implements Command {
		@Override
		public Receiver getReceiver() {
			return r;
		}

		@Override
		public Command getOppositeForUndo() {
			return new Summation(param, r);
		}
	}

	// Конкретная команда.
	public static record Multiplication(int param, Receiver r) implements Command {
		@Override
		public Receiver getReceiver() {
			return r;
		}

		@Override
		public Command getOppositeForUndo() {
			return new Division(param, r);
		}
	}

	// Конкретная команда.
	public static record Division(int param, Receiver r) implements Command {
		@Override
		public Receiver getReceiver() {
			return r;
		}

		@Override
		public Command getOppositeForUndo() {
			return new Multiplication(param, r);
		}
	}

	// Инициатор - обращается к команде для выполнения запроса
	public static class Invoker {
		private ArrayDeque<Command> undoingStack = new ArrayDeque<>(); // поддержка отмены команд. Для Redo потребовался
																		// бы второй стэк.

		public Command undo() {
			if (undoingStack.isEmpty())
				return null;
			else {
				Command last = undoingStack.pop();
				last.getOppositeForUndo().execute(last.getReceiver());
				return last;
			}
		}

		public void invoke(Command command, Receiver receiver) {
			undoingStack.push(command);
			command.execute(receiver);
		}

		public int stackSize() {
			return this.undoingStack.size();
		}
	}
}
