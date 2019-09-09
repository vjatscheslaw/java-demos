package com.example.demo.algorythms;

public class RecursiveSortAlgorythms {


    public static void main(String[] args) {

        long start = 0;
        int maxSize = 1000000;
        YArray array = new YArray(maxSize);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.mergeSort();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.shellSort();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.runQuickSort1();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

        array.populateArrayWithRandomValues();
//        array.display();
        start = System.currentTimeMillis();
        array.runQuickSort2();
//        array.display();
        System.out.println(System.currentTimeMillis() - start);

    }
}

class YArray {
    private long[] theArray;
    private int nElems;

    public YArray(int max) {
        theArray = new long[max];
        nElems = 0;
    }

    public void populateArrayWithRandomValues() {
        nElems = 0;
        for (int i = 0; i < theArray.length; i++) {
            insert(Math.round(Math.random() * 8000L));
        }
    }

    public void insert(long value) {
        theArray[nElems] = value;
        nElems++;
    }

    public int size() {
        return nElems;
    }

    public void display() {
        for (int j = 0; j < nElems; j++) {
            System.out.print(theArray[j] + " ");
        }
        System.out.println();
    }

    //O(N*log(N))
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

    // O(N^(3/2)) - O(N^(7/6))  NON RECURSIVE
    public void shellSort() {
        int in, out;
        long temp;
        int h = 1;  //для формулы интервальной последовательности Кнута
        while (h <= nElems/3)
            h = h*3 + 1;
        while (h > 0) {
            for (out = h; out < nElems; out++) {
                temp = theArray[out];
                in = out;

                while (in > h - 1  && theArray[in-h] >= temp)
                {
                    theArray[in] = theArray[in-h];
                    in -= h;
                }
                theArray[in] = temp;
            }
            h = (h - 1) / 3;
        }
    }
    //O(N)
    public int partitionIt(int left, int right, long pivot) {
        int leftPtr = left - 1;
        int rightPtr = right + 1;
        while (true) {
            while (leftPtr < right && theArray[++leftPtr] < pivot);
            while (rightPtr > left && theArray[--rightPtr] > pivot);
            if (leftPtr >= rightPtr)
                break;
            else
                swap(leftPtr, rightPtr);
            }
        return leftPtr;
    }

    public int partitionIt2(int left, int right, long pivot) {
        int leftPtr = left - 1;
        int rightPtr = right;
        while (true) {
            while (theArray[++leftPtr] < pivot);
            while (rightPtr > 0 && theArray[--rightPtr] > pivot);
            if (leftPtr >= rightPtr)
                break;
            else
                swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right);
        return leftPtr;
    }

    //to be used together with medianOf3()
    public int partitionIt3(int left, int right, long pivot) {
        int leftPtr = left;
        int rightPtr = right - 1;
        while (true) {
            while (theArray[++leftPtr] < pivot);
            while (theArray[--rightPtr] > pivot);
            if (leftPtr >= rightPtr)
                break;
            else
                swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right-1);
        return leftPtr;
    }

    //O(log(N)) for an unsorted array, but degrades to O(N^2) if the array is negatively sorted
    // and may cause Stack Overflow in this case
    public void runQuickSort1() {
        int left = 0;
        int right = nElems - 1;
        recQuickSort1(left, right);
    }

    //O(log(N)) and does not degrade to O(N^2)
    public void runQuickSort2() {
        int left = 0;
        int right = nElems - 1;
        recQuickSort2(left, right);
    }

    private void recQuickSort1(int left, int right) {
        if (right - left <= 0)
            return;
        else {
            long pivot = theArray[right];
            int partition = partitionIt2(left, right, pivot);
            recQuickSort1(left, partition -1);
            recQuickSort1(partition + 1, right);
        }
    }

    private void recQuickSort2(int left, int right) {
        int size = right - left + 1;
        if (size <= 3)
                manualSort(left, right);
        else {
            long pivot = medianOf3(left, right);
            int partition = partitionIt3(left, right, pivot);
            recQuickSort2(left, partition -1);
            recQuickSort2(partition + 1, right);
        }
    }

    private void swap(int one, int two) {
        long temp = theArray[one];
        theArray[one] = theArray[two];
        theArray[two] = temp;
    }

    public void manualSort(int left, int right) {
        int size = right - left + 1;
        if(size <= 1)
            return;
        if(size == 2)
        {
            if(theArray[left] > theArray[right])
                swap(left, right);
            return;
        }
        else
        {
            if(theArray[left] > theArray[right - 1])
                swap(left, right-1);
            if (theArray[left] > theArray[right])
                swap(left, right);
            if (theArray[right-1] > theArray[right])
                swap(right-1, right);
        }
    }

    private long medianOf3(int left, int right) {
        int center = (left + right) / 2;
        if(theArray[left] > theArray[center])
            swap(left, center);
        if(theArray[left] > theArray[right])
            swap(left, right);
        if(theArray[center] > theArray[right])
            swap(center, right);
        swap(center, right-1);
        return theArray[right-1];
    }

}





