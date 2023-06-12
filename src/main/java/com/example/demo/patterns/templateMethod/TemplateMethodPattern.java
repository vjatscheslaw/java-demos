package com.example.demo.patterns.templateMethod;

import java.time.LocalTime;

// Шаблонный метод - поведенческий шаблон проектирования, определяющий
// основу/каркас алгоритма и позволяющий наследникам переопределять
// некоторые шаги алгоритма, не изменяя его структуры в целом.
public class TemplateMethodPattern {

	static abstract class C {
		String templteMethod() {
			String result = "";
			System.out.println("====START-OF-TEMPLATE-METHOD====");
			result = result + function1();
			System.out.println(function2());
			System.out.println("====FINISH-OF-TEMPLATE-METHOD====");
			return result;
		}

		abstract String function1(); // изменяемая функциональность, за реализацию которой ответственны потомки

		String function2() { // изменяемая функциональность, с реализацией по умолчанию
			return "Всем привет";
		};
	}

	static class A extends C {
		@Override
		String function1() {
			return "333";
		}
	}

	static class B extends C {
		@Override
		String function1() {
			return "Триста тридцать три";
		}
	}

	static class Evening extends B {
		@Override
		String function2() {
			return "Добрый вечер";
		}
	}

	static class Morning extends B {
		@Override
		String function2() {
			return "Доброе утро";
		}
	}

	static class Day extends B {
		@Override
		String function2() {
			return "Добрый день";
		}
	}

	static class Night extends B {
		@Override
		String function2() {
			return "Доброй ночи";
		}
	}
}
