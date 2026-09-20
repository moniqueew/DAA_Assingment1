package Algorithms;


import Metrics.Metrics;

import java.util.Random;


public final class Partitioning {

    private static final Random RANDOM = new Random();

    private Partitioning() {
    }

    public static final class Bounds {
        public final int lt;
        public final int gt;

        public Bounds(int lt, int gt) {
            this.lt = lt;
            this.gt = gt;
        }
    }

    public static Bounds partition(int[] a, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1);
        int pivot = a[pivotIndex];

        int lt = lo;
        int i = lo;
        int gt = hi;

        while (i <= gt) {
            if (metrics != null) metrics.incComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        return new Bounds(lt, gt);
    }

    private static void swap(int[] a, int x, int y) {
        if (x == y) return;
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }
}

