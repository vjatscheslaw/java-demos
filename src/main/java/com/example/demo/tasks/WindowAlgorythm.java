package com.example.demo.tasks;

public class WindowAlgorythm {

    public static void main(String[] args) {

        int[] arry = new int[2000000];
        for (int i = 0; i < arry.length; i++) {
            arry[i] = (int) Math.round(Math.random()*1000);
        }
        final int WINDOW = 4;
        long start;
        start = System.currentTimeMillis();
        System.out.println(windowApproach(arry, WINDOW));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(bruteForceMaxSum(arry, WINDOW));
        System.out.println(System.currentTimeMillis() - start);


    }

    static int windowApproach(int arr[], int window)
    {
        int n = arr.length;

        // k must be greater
        if (n < window) {
            System.out.println("Invalid");
            return -1;
        }

        // Compute sum of first window of size k
        int max_sum = 0;
        for (int i = 0; i < window; i++)
            max_sum += arr[i];

        // Compute sums of remaining windows by
        // removing first element of previous
        // window and adding last element of
        // current window.
        int window_sum = max_sum;
        for (int i = window; i < n; i++) {
            window_sum += arr[i] - arr[i - window];
            max_sum = Math.max(max_sum, window_sum);
        }

        return max_sum;
    }


    static int bruteForceMaxSum(int arr[], int window)
    {
        int n = arr.length;
        // Initialize result
        int max_sum = Integer.MIN_VALUE;

        // Consider all blocks starting with i.
        for (int i = 0; i < n - window + 1; i++) {
            int localResult = 0;
            for (int j = 0; j < window; j++)
                localResult = localResult + arr[i + j];

            // Update result if required.
            max_sum = Math.max(localResult, max_sum);
        }

        return max_sum;
    }


}
