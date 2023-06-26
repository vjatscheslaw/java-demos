package com.example.demo;

import org.junit.Test;

public class MergeSortTest {


    @Test
     public void testMergeSort() {
        int maxSize = 10;
        DArray arr;
        arr = new DArray(maxSize);
        while (maxSize > 0) {
            arr.insert(Math.round(Math.random() * 100L));
            maxSize--;
        }
        arr.display();
        arr.mergeSort();
        arr.display();
    }

    class DArray {
        private long[] theArray;
        private int nElems;

        public DArray(int max) {
            theArray = new long[max];
            nElems=0;
        }

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

        public void mergeSort() {
            long[] workSpace = new long[nElems];
            recMergeSort(workSpace, 0, nElems-1);
        }

        private void recMergeSort(long[] workSpace, int lowerBound, int upperBound) {
            if(lowerBound == upperBound) return;
            else {
                int mid = (lowerBound + upperBound) / 2;
                recMergeSort(workSpace, lowerBound, mid);
                recMergeSort(workSpace, mid + 1, upperBound);
                merge(workSpace, lowerBound, mid + 1, upperBound);
            }
        }
        private void merge(long[] workSpace, int lowPtr, int highPtr, int upperBound) {
            int j = 0;
            int lowerBound = lowPtr;
            int mid = highPtr - 1;
            int n = upperBound - lowerBound + 1;

            while(lowPtr <= mid && highPtr <= upperBound) {
                if (theArray[lowPtr] < theArray[highPtr]) {
//                    System.out.println("workspace[" + j + "] = " + theArray[lowPtr]);
                    workSpace[j++] = theArray[lowPtr++];
                }
                else {
//                    System.out.println("workspace[" + j + "] = " + theArray[highPtr]);
                    workSpace[j++] = theArray[highPtr++];
                }
            }
                while (lowPtr <= mid) {
//                    System.out.println("workspace[" + j + "] = " + theArray[lowPtr]);
                    workSpace[j++] = theArray[lowPtr++];
                }
                while (highPtr <= upperBound) {
//                    System.out.println("workspace[" + j + "] = " + theArray[highPtr]);
                    workSpace[j++] = theArray[highPtr++];
                }
                for (j = 0; j < n; j++)
                    //System.out.println("Array[" + (lowerBound + j) + "] = workspace[" + j + "]");
                    theArray[lowerBound + j] = workSpace[j];
        }

    }


}
