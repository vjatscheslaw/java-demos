package com.example.demo.algorythms;

public class BinarySearch {

    public static void main(String[] args) {



    }

    class OrderedArray {
        private long[] theArray;
        private int nElems;

        public OrderedArray(int max) {
            theArray = new long[max];
            nElems = 0;
        }

        public void populateArrayWithOrderedValues() {
            nElems = 0;
            long current = 0;
            for (int i = 0; i < theArray.length; i++) {
                current = current + 3;
                theArray[i] = current;
                nElems++;
            }
        }

        //O(N)
        public void insert(long value) {
            int j;
            for (j = 0; j < nElems; j++)
                if (theArray[j] > value)
                    break;
            for (int k = nElems; k > j; k--)
                theArray[k] = theArray[k - 1];
            theArray[j] = value;
            nElems++;
        }

        public void display() {
            for (int j = 0; j < nElems; j++) {
                System.out.print(theArray[j] + " ");
            }
            System.out.println();
        }

        //O(N)
        public boolean delete(long value) {
            int j = find(value);
            if(j == nElems)
                return false;
            else {
                for(int k = j; k < nElems;  k++)
                    theArray[k] = theArray[k+1];
                nElems--;
                return true;
            }
        }

        //O(log(N))
        public int find(long searchKey) {
            int lowerBound = 0;
            int upperBound = nElems - 1;
            int current;

            while (true) {
                current = (lowerBound + upperBound) / 2;
                if(theArray[current] == searchKey) return current;
                else if (lowerBound > upperBound) return nElems;
                else {
                    if (theArray[current] < searchKey)
                        lowerBound = current + 1;
                    else
                        upperBound = current - 1;
                }
            }
        }


    }
}


