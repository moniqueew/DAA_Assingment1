package Algorithms;

import Metrics.Metrics;

public final class MergeSort {

    public static final int CUTOFF = 15;

    private MergeSort() {
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a.length < 2) return;
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, metrics);
    }

    private static void sort(int[] a, int[] buffer, int lo, int hi, Metrics metrics) {
        if (metrics != null) metrics.enterCall();
        try {
            int n = hi - lo + 1;
            if (n <= CUTOFF) {
                InsertionSort.sort(a, lo, hi, metrics);
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sort(a, buffer, lo, mid, metrics);
            sort(a, buffer, mid + 1, hi, metrics);
            merge(a, buffer, lo, mid, hi, metrics);
        } finally {
            if (metrics != null) metrics.exitCall();
        }
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics metrics) {
        if (metrics != null) metrics.incComparisons();
        if (a[mid] <= a[mid + 1]) {
            return;
        }

        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (metrics != null) metrics.incComparisons();
            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }
        while (i <= mid) {
            a[k++] = buffer[i++];
        }
        while (j <= hi) {
            a[k++] = buffer[j++];
        }
    }
}
