package Algorithms;

import Metrics.Metrics;

public final class InsertionSort {

    private InsertionSort() {
    }

    public static void sort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                if (metrics != null) metrics.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    public static void sort(int[] a, Metrics metrics) {
        sort(a, 0, a.length - 1, metrics);
    }
}

