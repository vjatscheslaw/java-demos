package com.example.demo.patterns.chainOfResponsibility;

import java.util.concurrent.ThreadLocalRandom;

// Цепочка Ответственности - поведенческий шаблон проектирования,
// предназначенный для организации в системе уровней ответственности.
public class ChainOfResponsibility {

	// Обработчик запросов (Handler)
	public enum Medics {

		// У нас будет шесть бинов типа Medics, по-разному настроенных. Это шесть конкретных обработчиков запросов.
		СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ(1, 1), СХОДИТЬ_НА_ПРИЁМ_К_ТЕРАПЕВТУ(2, 2), ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ(3, 10),
		ОБРАТИТЬСЯ_К_ВРАЧУ_СПЕЦИАЛИСТУ(4, 2), ЛЕЧЕНИЕ_В_ОБЛАСТНОЙ_БОЛЬНИЦЕ(6, 4), ЛЕЧЕНИЕ_В_ИЗРАИЛЕ(10, 6);

		int complexityOfDiseaseThreashold, severityOfIllnessThreashold; // пороги ответственности, свыше которых
																		// полномочия конкретного джава-бина
																		// заканчиваются, и он делегирует обработку
																		// запроса следующему бину по цепочке

		Medics(int complexityOfDiseaseThreashold, int severityOfIllnessThreashold) {
			this.complexityOfDiseaseThreashold = complexityOfDiseaseThreashold;
			this.severityOfIllnessThreashold = severityOfIllnessThreashold;
		}

		public void heal(Patient patient) {
			System.out.println("Пациент " + patient.toString() + " предпринял " + this.toString());

			// Паттерн Цепочка Ответственности позволяет избежать привязки отправителя
			// запроса к его получателю, давая шанс
			// обработать запрос нескольким объектам. Этот паттерн связывает
			// объекты-получатели в цепочку
			// и передаёт запрос вдоль этой цепочки, пока его не обработают. - GoF
			switch (this) {
			case СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ: {
				if (patient.getComplexityOfDisease() > 1 || patient.getSeverityOfIllness() > 1) {
					СХОДИТЬ_НА_ПРИЁМ_К_ТЕРАПЕВТУ.heal(patient);
				} else {
					patient.setComplexityOfDisease(0);
					patient.setSeverityOfIllness(0);
				}
				break;
			}
			case СХОДИТЬ_НА_ПРИЁМ_К_ТЕРАПЕВТУ: {
				if (patient.getComplexityOfDisease() > 2 && patient.getSeverityOfIllness() < 3) {
					patient.setSeverityOfIllness(1);
					ОБРАТИТЬСЯ_К_ВРАЧУ_СПЕЦИАЛИСТУ.heal(patient);
				} else if (patient.getSeverityOfIllness() > 2) {
					ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ.heal(patient);
				} else {
					patient.setComplexityOfDisease(1);
					patient.setSeverityOfIllness(1); // иногда от нахождения в очереди в поликлинике может заболеть голова
					СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ.heal(patient);
				}
				break;
			}
			case ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ: {
				patient.setSeverityOfIllness(0); // Морфий
				if (patient.getComplexityOfDisease() > 3) {
					ОБРАТИТЬСЯ_К_ВРАЧУ_СПЕЦИАЛИСТУ.heal(patient);
				} else {
					patient.setComplexityOfDisease(1);
					СХОДИТЬ_НА_ПРИЁМ_К_ТЕРАПЕВТУ.heal(patient);
				}
				break;
			}
			case ОБРАТИТЬСЯ_К_ВРАЧУ_СПЕЦИАЛИСТУ: {
				if (patient.getSeverityOfIllness() > 2) {
					patient.setComplexityOfDisease(1);
					ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ.heal(patient);
				} else if (patient.getComplexityOfDisease() > 4) {
					ЛЕЧЕНИЕ_В_ОБЛАСТНОЙ_БОЛЬНИЦЕ.heal(patient);
				} else {
					patient.setComplexityOfDisease(1);
					patient.setSeverityOfIllness(1);
					СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ.heal(patient);
				}
				break;
			}
			case ЛЕЧЕНИЕ_В_ОБЛАСТНОЙ_БОЛЬНИЦЕ: {
				if (patient.getSeverityOfIllness() > 4) {
					ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ.heal(patient);
				} else if (patient.getComplexityOfDisease() > 6) {
					ЛЕЧЕНИЕ_В_ИЗРАИЛЕ.heal(patient);
				} else {
					patient.setComplexityOfDisease(1);
					patient.setSeverityOfIllness(1);
					СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ.heal(patient);
				}
				break;
			}
			case ЛЕЧЕНИЕ_В_ИЗРАИЛЕ: {
				if (patient.getSeverityOfIllness() > 6) {
					ВЫЗВАТЬ_СКОРУЮ_ПОМОЩЬ_РЕАНИМАЦИЯ.heal(patient);
				} else {
					patient.setComplexityOfDisease(0);
					patient.setSeverityOfIllness(0);
				}
				break;
			}
			}
			;
		}
	}

	// клиент
	public static class Patient {
		String name;
		private int complexityOfDisease = 0;
		private int severityOfIllness = 0;

		public Patient(String name) {
			super();
			this.name = name;
		}

		public void getIll() {
			complexityOfDisease = ThreadLocalRandom.current().nextInt(1, 11);
			severityOfIllness = ThreadLocalRandom.current().nextInt(1, 11);
		}

		public int getComplexityOfDisease() {
			return complexityOfDisease;
		}

		public void setComplexityOfDisease(int complexityOfDisease) {
			this.complexityOfDisease = complexityOfDisease;
		}

		public int getSeverityOfIllness() {
			return severityOfIllness;
		}

		public void setSeverityOfIllness(int severityOfIllness) {
			this.severityOfIllness = severityOfIllness;
		}

		@Override
		public String toString() {
			return name + "[сложность:" + this.complexityOfDisease + ", тяжесть: " + this.severityOfIllness + "]";
		}
	}
}