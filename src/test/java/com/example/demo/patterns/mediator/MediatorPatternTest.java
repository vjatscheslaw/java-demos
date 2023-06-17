package com.example.demo.patterns.mediator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.mediator.MediatorPattern.*;

public class MediatorPatternTest {
	
	@Test
	void testMediatorPattern() {
		
		TextChat publicChat = new TextChat(); // <-- Это объект-медиатор

		User admin = new Admin(publicChat, "ADMIN"); // <-- коллега
		User user1 = new SimpleUser(publicChat, "Igor"); // <-- коллега
		User user2 = new SimpleUser(publicChat, "Alex"); // <-- коллега
		User user3 = new SimpleUser(publicChat, "Vasya"); // <-- коллега

		publicChat.setAdmin(admin);
		publicChat.addUser(user1);
		publicChat.addUser(user2);
		publicChat.addUser(user3);
		
		admin.sendMessage("ohoho"); 
		user1.sendMessage("Привет");
		user2.sendMessage("Zdorova");
		user3.sendMessage("Привет");
		
		Assertions.assertAll("Все пользователи публичного чата получили одинаковое количество сообщений", () -> {
			Assertions.assertEquals(4, admin.getMessagesCount());
			Assertions.assertEquals(4, user1.getMessagesCount());
			Assertions.assertEquals(4, user2.getMessagesCount());
			Assertions.assertEquals(4, user3.getMessagesCount());
		});
	}

}
