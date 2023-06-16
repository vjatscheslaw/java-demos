package com.example.demo.patterns.chainOfResponsibility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.chainOfResponsibility.ChainOfResponsibility.*;

public class ChainOfResponsibilityTest {
	
	@Test
	void chainOfResponsibilityTest() {
		Patient pat1 = new Patient("Ирина");
		Patient pat2 = new Patient("Ольга");
		Patient pat3 = new Patient("Аглая");
		
		Assertions.assertEquals(0, pat1.getComplexityOfDisease());
		Assertions.assertEquals(0, pat1.getSeverityOfIllness());
		Assertions.assertEquals(0, pat2.getComplexityOfDisease());
		Assertions.assertEquals(0, pat2.getSeverityOfIllness());
		Assertions.assertEquals(0, pat3.getComplexityOfDisease());
		Assertions.assertEquals(0, pat3.getSeverityOfIllness());
		
		pat1.getIll();
		pat2.getIll();
		pat3.getIll();
		
		Assertions.assertNotEquals(0, pat1.getComplexityOfDisease());
		Assertions.assertNotEquals(0, pat1.getSeverityOfIllness());
		Assertions.assertNotEquals(0, pat2.getComplexityOfDisease());
		Assertions.assertNotEquals(0, pat2.getSeverityOfIllness());
		Assertions.assertNotEquals(0, pat3.getComplexityOfDisease());
		Assertions.assertNotEquals(0, pat3.getSeverityOfIllness());
		
		Medics.СХОДИТЬ_В_АПТЕКУ_САМОЛЕЧЕНИЕ.heal(pat1);
		Medics.СХОДИТЬ_НА_ПРИЁМ_К_ТЕРАПЕВТУ.heal(pat2);
		Medics.ОБРАТИТЬСЯ_К_ВРАЧУ_СПЕЦИАЛИСТУ.heal(pat3);
		
		Assertions.assertEquals(0, pat1.getComplexityOfDisease());
		Assertions.assertEquals(0, pat1.getSeverityOfIllness());
		Assertions.assertEquals(0, pat2.getComplexityOfDisease());
		Assertions.assertEquals(0, pat2.getSeverityOfIllness());
		Assertions.assertEquals(0, pat3.getComplexityOfDisease());
		Assertions.assertEquals(0, pat3.getSeverityOfIllness());
		
	}
}
