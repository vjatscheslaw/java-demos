package com.example.demo.patterns.memento;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.memento.MementoPattern.DraftDrawer;

public class MementoPatternTest {

	@Test
	void testPassivationUsingMemento() {

		// Клиентский код, знающий и использующий объекты типа Хранитель, называется Originator (Хозяин). 
		// Хранитель сохраняет внутренее состояние Хозяина.
		MementoPattern.EMail eMail = new MementoPattern.EMail("admin@bash.org", "Закрывай свойю лавочгу, падонак!");
		eMail.note("Добрый день!");
		eMail.note("Хотя он может и не добрый");
		eMail.note("Но все же добрый");
		eMail.note("Стакан полностью полный");
		System.out.println(eMail.getContents());
		Assertions.assertEquals(4, Pattern.compile(".*[\r\n]{1}.*").matcher(eMail.getContents()).results().count());

		// устанавливаем значение транзитивного поля, которое не сериализуется.
		eMail.setSubscribedAs("С уважением,\nЕгадзил Парфенонович");
		Assertions.assertFalse(Pattern.compile("^Моё увожение.*").matcher(eMail.getSubscribedAs()).find());
		Assertions.assertTrue(Pattern.compile("^.*[\\r\\n]Егадзил Парфенонович$").matcher(eMail.getSubscribedAs()).find());

		String draftFilePath = "/tmp/mementoPatternTest/EMail_deflated_draft_" + ((Object) eMail).toString();
		
		DraftDrawer.saveDraft(eMail, draftFilePath); // Сохраняем

		eMail = null; // Очистим память
		Assertions.assertNull(eMail);
		
		MementoPattern.EMail draftMail = DraftDrawer.loadDraft(draftFilePath); // Восстанавливаем;			
		Assertions.assertNotNull(draftMail);
		
		Assertions.assertNull(draftMail.getSubscribedAs()); // Транзитивные поля при восстановлении будут null
		Assertions.assertEquals(4, Pattern.compile(".*[\r\n]{1}.*").matcher(draftMail.getContents()).results().count()); // все остальные строки на месте
		draftMail.note("Продолжим наши изыскания");
		draftMail.note("Позже");
		draftMail.note("Возможно");
		Assertions.assertEquals(7, Pattern.compile(".*[\r\n]{1}.*").matcher(draftMail.getContents()).results().count()); // предыдущая работа не пропала
		
		System.out.println(draftMail.print());
	}

}
