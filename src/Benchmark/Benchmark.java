package Benchmark;

import Algorithms.MergeSort;
import Algorithms.QuickSelect;
import Algorithms.QuickSort;
import Metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int REPEATS = 5;
    private static final long SEED = 42L;

    public static void main(String[] args) throws IOException {
        String outputPath = args.length > 0 ? args[0] : "results.csv";
        try (PrintWriter out = new PrintWriter(new FileWriter(outputPath))) {
            out.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                for (String inputType : INPUT_TYPES) {
                    runCase(out, "MergeSort", inputType, n);
                    runCase(out, "QuickSort", inputType, n);
                    runCase(out, "QuickSelect", inputType, n);
                    System.out.println("done n=" + n + " input=" + inputType);
                }
            }
        }
        System.out.println("Benchmark finished -> " + outputPath);
    }

    private static void runCase(PrintWriter out, String algorithm, String inputType, int n) {
        double[] times = new double[REPEATS];
        long[] comparisons = new long[REPEATS];
        int[] depths = new int[REPEATS];

        for (int r = 0; r < REPEATS; r++) {
            int[] data = generateInput(inputType, n, SEED + r);
            Metrics metrics = new Metrics();
            metrics.startTimer();

            switch (algorithm) {
                case "MergeSort" -> MergeSort.sort(data, metrics);
                case "QuickSort" -> QuickSort.sort(data, metrics);
                case "QuickSelect" -> QuickSelect.select(data, n / 2, metrics);
                default -> throw new IllegalArgumentException("Unknown algorithm " + algorithm);
            }

            metrics.stopTimer();
            times[r] = metrics.getElapsedMillis();
            comparisons[r] = metrics.getComparisons();
            depths[r] = metrics.getMaxDepth();
        }

        int medianIdx = medianIndex(times);
        out.printf("%s,%s,%d,%.4f,%d,%d%n",
                algorithm, inputType, n, times[medianIdx], comparisons[medianIdx], depths[medianIdx]);
    }

    private static int medianIndex(double[] times) {
        Integer[] idx = new Integer[times.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare(times[a], times[b]));
        return idx[idx.length / 2];
    }

    private static int[] generateInput(String type, int n, long seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        switch (type) {
            case "random" -> {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
            }
            case "sorted" -> {
                for (int i = 0; i < n; i++) a[i] = i;
            }
            case "duplicates" -> {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10);
            }
            default -> throw new IllegalArgumentException("Unknown input type " + type);
        }
        return a;
    }
}

