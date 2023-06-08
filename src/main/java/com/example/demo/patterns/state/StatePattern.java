package com.example.demo.patterns.state;

// Состояние это поведенческий шаблон проектирования. Используется в тех случаях,
// своё поведение при смене своего типа-состояния.
public class StatePattern {

	sealed interface Station permits Radio7, HIT_FM, VestiFM {
		void play();
	}

	static final class Radio7 implements Station {

		@Override
		public void play() {
			System.out.println("Radio 7 .....");
		}
	}

	static final class HIT_FM implements Station {

		@Override
		public void play() {
			System.out.println("HIT FM .....");
		}
	}

	static final class VestiFM implements Station {

		@Override
		public void play() {
			System.out.println("Vesti FM .....");
		}
	}

// Контекст. В зависимости от своего состояния, этот контекст меняет своё поведение.
// Переключение между состояниями происходит внутри контекста, а не внутри самого объекта состояния
	static class Radio {
		Station station;

		public void setStation(Station station) {
			this.station = station;
		}

		void nextStationUsingInstanceof() {
			if (station instanceof Radio7)
				this.setStation(new VestiFM()); // переключение между конкретными состояниями внутри контекста
			else if (station instanceof VestiFM)
				this.setStation(new HIT_FM());
			else if (station instanceof HIT_FM)
				this.setStation(new Radio7());
		}

		void nextStationUsingGetClass() {
			if (station.getClass() == Radio7.class)
				this.setStation(new VestiFM());
			else if (station.getClass() == VestiFM.class)
				this.setStation(new HIT_FM());
			else if (station.getClass() == HIT_FM.class)
				this.setStation(new Radio7());
		}

		// If you want to compile these, do it with --enable-preview -source 19
//		void nextStation() {
//			switch (station) {
//			case Radio7 s1 ->
//				this.setStation(new VestiFM());
//			case VestiFM s2 ->
//				this.setStation(new HIT_FM());
//			case HIT_FM s3 ->
//				this.setStation(new Radio7());
//			default ->
//				throw new ClassCastException("Unknown Radio Station");
//			}
//		}

		void play() {
			station.play();
		}
	}
}
