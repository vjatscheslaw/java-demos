package com.example.demo.patterns.strategy;

// поведенческий шаблон проектирования, предназначенный для определения семейства алгоритмов,
// инкапсуляции каждого из них и обеспечения их взаимозаменяемости. Это позволяет выбирать
// алгоритм путём определения соответствующего класса.
// Шаблон Strategy позволяет менять выбранный алгоритм независимо от объектов-клиентов, которые его используют.
public class StrategyTemplate {

    public static void main(String[] args) {
        int maxSize = 10;
        DArray arr;
        arr = new DArray(maxSize);
        while (maxSize > 0) {
            arr.insert(Math.round(Math.random() * 100L));
            maxSize--;
        }
        arr.display();
        StrategyClient client = new StrategyClient();
        client.setStrategy(new MergeSortStrategy());
        client.performOperation(arr);
        arr.display();
    }

}

interface Strategy {
    void operation(DArray array);
}

class MergeSortStrategy implements Strategy {
    @Override
    public void operation(DArray array) {
        array.mergeSort();
    }
}

class Strategy2 implements Strategy {
    @Override
    public void operation(DArray array) {
        System.out.println("Strategy2");
    }
}

class StrategyClient {

    Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    void performOperation(DArray array) {
        strategy.operation(array);
    }
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

    public void recMergeSort(long[] workSpace, int lowerBound, int upperBound) {
        if(lowerBound == upperBound) return;
        else {
            int mid = (lowerBound + upperBound) / 2;
            recMergeSort(workSpace, lowerBound, mid);
            recMergeSort(workSpace, mid + 1, upperBound);
            merge(workSpace, lowerBound, mid + 1, upperBound);
        }
    }
    public void merge(long[] workSpace, int lowPtr, int highPtr, int upperBound) {
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

