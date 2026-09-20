package Algorithms;

import Metrics.Metrics;

public final class QuickSelect {

    private QuickSelect() {
    }

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k=" + k + " is out of range for array of length " + a.length);
        }
        return select(a, 0, a.length - 1, k, metrics);
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics metrics) {
        if (metrics != null) metrics.enterCall();
        try {
            while (true) {
                if (lo == hi) {
                    return a[lo];
                }
                Partitioning.Bounds b = Partitioning.partition(a, lo, hi, metrics);
                if (k < b.lt) {
                    hi = b.lt - 1;
                } else if (k > b.gt) {
                    lo = b.gt + 1;
                } else {
                    return a[k];
                }
            }
        } finally {
            if (metrics != null) metrics.exitCall();
        }
    }
}
