package Metrics;

public class Metrics {

    private long comparisons = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private long startNanos = 0;
    private long elapsedNanos = 0;

    public void incComparisons() {
        comparisons++;
    }

    public void incComparisons(long count) {
        comparisons += count;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void enterCall() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitCall() {
        currentDepth--;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void startTimer() {
        startNanos = System.nanoTime();
    }

    public void stopTimer() {
        elapsedNanos = System.nanoTime() - startNanos;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public double getElapsedMillis() {
        return elapsedNanos / 1_000_000.0;
    }

    public void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
        startNanos = 0;
        elapsedNanos = 0;
    }

    @Override
    public String toString() {
        return "Metrics{comparisons=" + comparisons +
                ", maxDepth=" + maxDepth +
                ", timeMs=" + getElapsedMillis() + "}";
    }
}
