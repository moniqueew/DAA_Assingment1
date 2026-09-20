package Algorithms;

import Metrics.Metrics;

public final class QuickSort {

    private QuickSort() {
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a.length < 2) return;
        sort(a, 0, a.length - 1, metrics);
    }

    private static void sort(int[] a, int lo, int hi, Metrics metrics) {
        if (metrics != null) metrics.enterCall();
        try {
            while (lo < hi) {
                if (hi - lo + 1 <= MergeSort.CUTOFF) {
                    InsertionSort.sort(a, lo, hi, metrics);
                    return;
                }

                Partitioning.Bounds b = Partitioning.partition(a, lo, hi, metrics);
                int leftLen = b.lt - lo;
                int rightLen = hi - b.gt;

                if (leftLen < rightLen) {
                    sort(a, lo, b.lt - 1, metrics);
                    lo = b.gt + 1;
                } else {
                    sort(a, b.gt + 1, hi, metrics);
                    hi = b.lt - 1;
                }
            }
        } finally {
            if (metrics != null) metrics.exitCall();
        }
    }
}
