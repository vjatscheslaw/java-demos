package com.example.demo.patterns.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.state.StatePattern.Radio;
import com.example.demo.patterns.state.StatePattern.Radio7;
import com.example.demo.patterns.state.StatePattern.Station;

public class StatePatternTest {
	
	@Test
	void testSwitchState() {
		Radio radio = new Radio();
		radio.setStation((Station) new Radio7());
		Assertions.assertTrue(radio.station instanceof StatePattern.Radio7);
		radio.play();
		radio.nextStationUsingInstanceof();
		Assertions.assertTrue(radio.station instanceof StatePattern.VestiFM);
		radio.play();
		radio.nextStationUsingInstanceof();
		Assertions.assertTrue(radio.station instanceof StatePattern.HIT_FM);
		radio.play();
		radio.nextStationUsingGetClass();
		Assertions.assertTrue(radio.station instanceof StatePattern.Radio7);
		radio.play();
		radio.nextStationUsingGetClass();
		Assertions.assertTrue(radio.station instanceof StatePattern.VestiFM);
		radio.play();
		radio.nextStationUsingGetClass();
		Assertions.assertTrue(radio.station instanceof StatePattern.HIT_FM);
		radio.play();
		Assertions.assertTrue(radio.station instanceof StatePattern.HIT_FM);
		radio.play();
		Assertions.assertTrue(radio.station instanceof StatePattern.HIT_FM);
		radio.play();
		
	}
	

}
