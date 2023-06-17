package com.example.demo.patterns.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Посредник (aka медиатор) - поведенческий шаблон проектирования, обеспечивающий взаимодействие множества объектов,
// формируя при этом слабую связанность и избавляя объекты от необходимости явно ссылаться друг на друга.
public class MediatorPattern {

	// Интерфейс коллеги. Классы, взаимодействующие друг с другом посредством Посредника, принято называть Коллегами.
	 static abstract class User {
		Chat chat;
		String name;

		public User(Chat chat, String name) {
			this.chat = chat;
			this.name = name;
		}

		public void sendMessage(String message) {
			chat.sendMessage(message, this);
		}

		abstract void getMessage(String message);

		String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			return Objects.equals(name, other.name);
		}

		@Override
		public String toString() {
			return "User [" + "name='" + name + '\'' + "']";
		}

		protected abstract int getMessagesCount();
	}

	// Интерфейс посредника
	interface Chat {
		void sendMessage(String message, User user);
	}

	// Конкретный коллега
	static class Admin extends User {
		private int messagesCount = 0;
		public Admin(Chat chat, String name) {
			super(chat, name);
		}

		@Override
		public void sendMessage(String message) {
			this.messagesCount++;
			chat.sendMessage(message, this);
		}

		@Override
		public void getMessage(String message) {
			this.messagesCount++;
			System.out.println("Администратор " + getName() + " получил сообщение: '" + message + "'");
		}
		
		@Override
		public int getMessagesCount() {
			return messagesCount;
		}
	}

	// Конкретный коллега
	static class SimpleUser extends User {
		private int messagesCount = 0;
		public SimpleUser(Chat chat, String name) {
			super(chat, name);
		}

		@Override
		public void sendMessage(String message) {
			this.messagesCount++;
			chat.sendMessage(message, this);
		}

		@Override
		public void getMessage(String message) {
			this.messagesCount++;
			System.out.println("Пользователь " + getName() + " получил сообщение: '" + message + "'");
		}

		@Override
		public int getMessagesCount() {
			return messagesCount;
		}

	}

	// Конкретный посредник (Медиатор)
	static class TextChat implements Chat {
		User admin;
		List<User> users = new ArrayList<>();

		public void setAdmin(User admin) {
			if (admin instanceof Admin a) {
				this.admin = a;
			} else {
				throw new RuntimeException("Недостаточно прав");
			}
		}

		public void addUser(User u) {
			if (admin == null) {
				throw new RuntimeException("У паблика нет админа, который мог бы добавить пользователя");
			} else if (u instanceof SimpleUser simp) {
				users.add(simp);
				System.out.println("Добавлен пользоваель");
			} else if (u instanceof Admin a) {
				users.add(a);
				System.out.println("Добавлен администратор");
			} else
				throw new RuntimeException("Неизвестная роль");
		}

		// Паттерн Посредник локализует поведение, которое, в противном случае, пришлось бы распределять между несколькими объектами.
		// Устраняет связанность между коллегами. Упрощает протоколы взаимодействия объектов - один посредник взаимодействует со всеми коллегами, 
		// вместо того, чтобы все коллеги взаимодействовали со всеми коллегами. "Один ко многим" вместо "многие ко многим".
		// Медиатор централизует управление - переносит сложность взаимодействия коллег в класс-посредник. 
		// В результате, сам Посредник может стать монолитом, который трудно сопровождать - GoF
		public void sendMessage(String message, User user) {
			if (user instanceof Admin a) {
				for (User u : users) {
					u.getMessage(message + " от " + a.getName());
				}
			} else if (user instanceof SimpleUser simp) {
				for (User u : users) {
					if (!Objects.equals(u, simp)) {
						u.getMessage(message + " от " + simp.getName());
					}
				}
				admin.getMessage(message + " от " + simp.getName());
			}
		}
	}
}
