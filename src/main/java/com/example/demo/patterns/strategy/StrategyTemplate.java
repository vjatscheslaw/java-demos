package com.example.demo.patterns.strategy;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

// Стратегия - поведенческий шаблон проектирования, предназначенный для определения семейства алгоритмов,
// инкапсуляции каждого из них и обеспечения их взаимозаменяемости. Это позволяет выбирать
// алгоритм путём определения соответствующего класса.
// Шаблон Strategy позволяет менять выбранный алгоритм независимо от объектов-клиентов, которые его используют.
public class StrategyTemplate {

	//Клиентский код при использовании паттерна Стратегия должен быть способен во время исполнения программы менять стратегию/объект, реализующий функциональность
	static class ArrayOfLongProcessingClient {
		private ArrayOfLongProcessingStrategy strategy = null;

		public void setStrategy(ArrayOfLongProcessingStrategy strategy) {
			this.strategy = strategy;
		}

		long[] performOperation(long[] data) {
			if (Objects.isNull(strategy)) throw new RuntimeException("Сначала определитесь со стратегией");
			return this.strategy.applyStrategy(data);
		}
	}
	
	private interface ArrayOfLongProcessingStrategy {
		long[] applyStrategy(long[] array);
	}

	static class MergeSortingArrayStrategy implements ArrayOfLongProcessingStrategy {
		private long[] theArray;
		private int nElems;

		// Если бы мы реализовали наши объекты-стратегии в виде перечислений (enum), тогда они еще были бы Одиночками (Singleton). Не в этот раз :-)
		public MergeSortingArrayStrategy() { 
		}

		public long[] applyStrategy(long[] data) {
			theArray = new long[data.length];
			System.arraycopy(data, 0, theArray, 0, data.length);
			nElems = data.length;
			long[] workSpace = new long[nElems];
			recursiveMergeSort(workSpace, 0, nElems - 1);
			return theArray;
		}

		private void recursiveMergeSort(long[] workSpace, int lowerBound, int upperBound) {
			if (lowerBound == upperBound)
				return;
			else {
				int mid = (lowerBound + upperBound) / 2;
				recursiveMergeSort(workSpace, lowerBound, mid);
				recursiveMergeSort(workSpace, mid + 1, upperBound);
				merge(workSpace, lowerBound, mid + 1, upperBound);
			}
		}

		private void merge(long[] workSpace, int lowPtr, int highPtr, int upperBound) {
			int j = 0;
			int lowerBound = lowPtr;
			int mid = highPtr - 1;
			int n = upperBound - lowerBound + 1;

			while (lowPtr <= mid && highPtr <= upperBound) {
				if (theArray[lowPtr] < theArray[highPtr]) {
					workSpace[j++] = theArray[lowPtr++];
				} else {
					workSpace[j++] = theArray[highPtr++];
				}
			}
			while (lowPtr <= mid) {
				workSpace[j++] = theArray[lowPtr++];
			}
			while (highPtr <= upperBound) {
				workSpace[j++] = theArray[highPtr++];
			}
			for (j = 0; j < n; j++)
				theArray[lowerBound + j] = workSpace[j];
		}
	}
	
	static class HeapSortingArrayStrategy implements ArrayOfLongProcessingStrategy {
		private long[] priorityQueue;
	    private int minPriorityElementIndex;

	    public HeapSortingArrayStrategy() {
	    	this.priorityQueue = new long[1];
	        this.minPriorityElementIndex = 0;
	    }
	    
	    public long[] applyStrategy(long[] data) {
	    	for (long n : data) this.insert(n);
	    	long[] rslt = new long[data.length];
	    	for (int i = data.length - 1; i >= 0; i--) {
	    		try {
	    			rslt[i] = this.pop();
	    		} catch (NoSuchElementException nsex) {
	    			System.out.println("here");
	    		}
	    	}
	    	return rslt;
	    }

	    public void insert(long key) {
	        if (minPriorityElementIndex == priorityQueue.length - 1) {
	            long[] biggerPriorityQueue = new long[priorityQueue.length * 2];
	            System.arraycopy(priorityQueue, 1, biggerPriorityQueue, 1, priorityQueue.length - 1);
	            priorityQueue = biggerPriorityQueue;
	        }

	        this.priorityQueue[++minPriorityElementIndex] = key;
	        swim(minPriorityElementIndex);
	    }
	    
	    public long pop() {
	        if (minPriorityElementIndex < 1) throw new NoSuchElementException();
	        long max = priorityQueue[1];
	        
	        if (minPriorityElementIndex == 1) {
				minPriorityElementIndex = 0;				
				return max;
			}
	        
	        swap(1, minPriorityElementIndex--);
	        sink(1);

	        if (minPriorityElementIndex == priorityQueue.length - (priorityQueue.length / 4) - 1) {
	            long[] smallerPriorityQueue = new long[minPriorityElementIndex + 1];
	            System.arraycopy(priorityQueue, 1, smallerPriorityQueue, 1, smallerPriorityQueue.length - 1);
	            priorityQueue = smallerPriorityQueue;
	        }
	        return max;
	    }
	    
	    /**
	     * Идея такова: чем ниже приоритет у элемента, тем он более "плотный" относительно остальных элементов "среды" кучи, 
	     * поэтому он и тонет в них, до тех пор, пока не займёт 
	     * своего места (не окажется менее "плотным", то есть, более приоритетным, чем какой-то другой элемент)
	     * @param индекс элемента, который должен найти свре место в куче, потопляясь.
	     */
	    private void sink (int index) {
	        while (2 * index <= minPriorityElementIndex) {
	            int j = 2 * index; // Индексная арифметка. Каждый родитель с индексом k может иметь лишь двух потомков с индексами 2k и 2k + 1, соответственно. Обход кучи производится индексной арифметикой.
	            if (j < minPriorityElementIndex && priorityQueue[j] < priorityQueue[j + 1]) j++; // При "потоплении", мы должны поменять местами родителя со старшим (по приоритету) из его двух потомков. Вот почему мы сравниваем элементы с индексами 'j' и 'j + 1'. Для каждых двух элементов с индексами 'j' и 'j + 1', их родительский элемент с индексом j / 2 имеет более высокий приоритет.
	            if (priorityQueue[index] > priorityQueue[j]) break;
	            swap(index, j);
	            index = j;
	        }
	    }

	    /**
	     * Идея такова: чем выше приоритет у элемента, тем он менее "плотный" относительно остальных элементов "среды" кучи, 
	     * поэтому он и всплывает относительно них, до тех пор, пока не займёт 
	     * своего места (не окажется более "плотным", то есть, менее приоритетным, чем какой-то другой элемент)
	     * @param индекс элемента, который должен найти свре место в куче, всплывая.
	     */
	    private void swim(int index) {
	        while (index > 1 && priorityQueue[index / 2] < priorityQueue[index]) { // Навигация по куче на основе массива осуществляется индексной арифметикой
	            swap(index, index / 2);
	            index = index / 2;
	        }
	    }

	    private void swap (int i, int j) {
	        long temp = priorityQueue[j];
	        priorityQueue[j] = priorityQueue[i];
	        priorityQueue[i] = temp;
	    }
	}
}
