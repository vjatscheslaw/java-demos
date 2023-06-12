package com.example.demo.patterns.strategy;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.patterns.strategy.StrategyTemplate.ArrayOfLongProcessingClient;

public class StrategyTemplateTest {
	
	@Test
	void testStrategy() {
		int maxSize = 10;
		long[] data = new long[maxSize];
        while (maxSize > 0) data[--maxSize] = (Math.round(Math.random() * 1000L));
        maxSize = 10;
        ArrayOfLongProcessingClient client = new ArrayOfLongProcessingClient();
        boolean sorted = true;
        long lastMax = Long.MIN_VALUE;
        for (int i = 0; i < maxSize; i++) {
        	if (data[i] < lastMax) {
        		sorted = false;
        		break;
        	} else {
        		lastMax = data[i];
        	}
        }
        Assertions.assertFalse(sorted);
        
        client.setStrategy(new StrategyTemplate.MergeSortingArrayStrategy());
        long[] resultOfMergeSort = client.performOperation(data);
        for (int i = 1; i < maxSize; i++) {
        	Assertions.assertTrue(resultOfMergeSort[i  - 1] <= resultOfMergeSort[i]);
        }
        
        client.setStrategy(new StrategyTemplate.HeapSortingArrayStrategy());
        long[] resultOfHeapSort = client.performOperation(data);
        for (int i = 1; i < maxSize; i++) {
//        	System.out.println(i);
        	Assertions.assertTrue(resultOfHeapSort[i  - 1] <= resultOfHeapSort[i]);
        }
	}
}
