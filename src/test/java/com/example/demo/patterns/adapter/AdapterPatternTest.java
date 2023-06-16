package com.example.demo.patterns.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.adapter.AdapterPattern.ModernLibraryKit;
import com.example.demo.patterns.adapter.AdapterPattern.ModernLibraryKit2;
import com.example.demo.patterns.adapter.AdapterPattern.ModernInterface;

public class AdapterPatternTest {

	@Test
	void testAdapter() {
		ModernInterface charPrinter1 = new ModernLibraryKit();
		ModernInterface charPrinter2 = new ModernLibraryKit2();
		Assertions.assertEquals(new String(new char[]{(char) 0x0293 }), charPrinter1.printEzh());
		Assertions.assertEquals(new String(new char[]{(char) 0x02A4 }), charPrinter1.printDezh());
		Assertions.assertEquals(new String(new char[]{(char) 0x0293 }), charPrinter2.printEzh());
		Assertions.assertEquals(new String(new char[]{(char) 0x02A4 }), charPrinter2.printDezh());
	}
}
