package com.example.demo.algorythms;

public class SimpleSortAlgs {


    public static void main(String[] args) {

        long start = 0;
        int maxSize = 200000;
        DArray array = new DArray(maxSize);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.bubbleSort();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.selectionSort();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.insertionSort();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

    }
}

class DArray {
    private long[] theArray;
    private int nElems;

    public DArray(int max) {
        theArray = new long[max];
        nElems = 0;
    }

    public void populateArrayWithRandomValues() {
        nElems = 0;
        for (int i = 0; i < theArray.length; i++) {
            theArray[i] = Math.round(Math.random() * 8000L);
            nElems++;
        }
    }

    //O(1)
    public void insert(long value) {
        theArray[nElems] = value;
        nElems++;
    }

    public void display() {
        for (int j = 0; j < nElems; j++) {
            System.out.print(theArray[j] + " ");
        }
        System.out.println();
    }

    public void bubbleSort() {
        int out, in;
        for (out = nElems - 1; out > 1; out--) {
            for (in = 0; in < out; in++) {
                if (theArray[in] > theArray[in + 1]) {
                    swap(in, in + 1);
                }
            }
        }
    }

    public void selectionSort() {
        int out, in, min;
        for (out = 0; out < nElems - 1; out++) {
            min = out;
            for (in = out + 1; in < nElems; in++) {
                if (theArray[in] < theArray[min]) {
                    min = in;
                }
            }
            swap(out, min);
        }
    }
    //(N^2)/2 - the average time
    public void insertionSort() {
        int out, in;
        for (out = 1; out < nElems; out++) {
            long temp = theArray[out];
            in = out;
            while (in > 0 && theArray[in-1] >= temp) {
                theArray[in] = theArray[in-1];
                in--;
            }
            theArray[in] = temp;
        }
    }


    private void swap(int one, int two) {
        long temp = theArray[one];
        theArray[one] = theArray[two];
        theArray[two] = temp;
    }

}

