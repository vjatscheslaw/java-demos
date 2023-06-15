package com.example.demo.patterns.memento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

// Хранитель(Memento), известный также как Лексема(Token)
// поведенческий шаблон проектирования, позволяющий, не нарушая инкапсуляцию,
// зафиксировать и сохранить внутреннее состояние объекта так,
// чтобы позднее восстановить его в это состояние.
public class MementoPattern {

	// Объектом-хранителем выступает класс EMail.
	static public class EMail implements Serializable {
		private static final long serialVersionUID = -43895793487L;

		private String recipient = "";
		private String topic = "";
		private String contents = "";
		private transient String subscribedAs = "Моё увожение, вот это вы кросавчеги!";

		public EMail(String... params) {
			if (params.length > 0)
				this.recipient = params[0];
			if (params.length > 1)
				this.topic = params[1];
			if (params.length > 2)
				this.subscribedAs = params[2];
		}

		public String getRecipient() {
			return recipient;
		}

		public void setRecipient(String title) {
			this.recipient = title;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getContents() {
			return contents;
		}

		public void setContents(String contents) {
			this.contents = contents;
		}

		public String getSubscribedAs() {
			return subscribedAs;
		}

		public void setSubscribedAs(String subscribedAs) {
			this.subscribedAs = subscribedAs;
		}

		public void note(String text) {
			this.contents = this.contents + "\n" + text;
		}

		public String print() {
			return "========RECIPIENT=========\n" + this.recipient + "\n============TOPIC==========\n" + this.topic
					+ "\n========CONTENTS========\n" + this.contents + "\n==========END=========\n";
		}

		@Override
		public int hashCode() {
			return Objects.hash(contents, recipient, topic);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EMail other = (EMail) obj;
			return Objects.equals(contents, other.contents) && Objects.equals(recipient, other.recipient)
					&& Objects.equals(topic, other.topic);
		}

	}

	// Этот класс представляет собой Посыльного (Caretaker) - механизм отката,
	// который отвечает за сохранение хранителя,
	// не производит никаких операций над хранителем и не исследует его внутреннее
	// содержимое. - GoF
	public static class DraftDrawer {

		// Caretaker композиционно содержит в себе Хранителя. В данном случае, в качестве переданного
		// в статический метод параметра eMail.
		public static void saveDraft(EMail eMail, String draftFilePath) {
			// Вот тут происходит сохранение черновика письма в файл, для того, чтобы можно
			// было продолжить работать над ним позже
			try (ObjectOutputStream outputStream = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(Paths.get(draftFilePath).toFile())))) {
				outputStream.writeObject(eMail);
			} catch (Exception e) {
				System.err.println("--=ERROR=--");
				e.printStackTrace();

			}
		}

		public static EMail loadDraft(String draftFilePath) {
			try (ObjectInputStream inputStream = new ObjectInputStream(
					new InflaterInputStream(new FileInputStream(Paths.get(draftFilePath).toFile())))) {
				return (MementoPattern.EMail) inputStream.readObject();
			} catch (Exception e) {
				System.err.println("--=ERROR=--");
				e.printStackTrace();
				return null;
			}	
		}
	}
}
