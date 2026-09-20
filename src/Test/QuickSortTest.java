import Algorithms.QuickSort;
import Metrics.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickSortTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 16, 50, 200, 1000})
    void matchesArraysSortOnRandomArrays(int size) {
        Random rnd = new Random(size + 99);
        for (int trial = 0; trial < 15; trial++) {
            int[] a = randomArray(size, rnd);
            int[] expected = a.clone();
            Arrays.sort(expected);

            QuickSort.sort(a, new Metrics());
            assertArrayEquals(expected, a, "size=" + size + " trial=" + trial);
        }
    }

    @Test
    void emptyArray() {
        int[] a = {};
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void singleElement() {
        int[] a = {7};
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{7}, a);
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[2000];
        Arrays.fill(a, 3);
        QuickSort.sort(a, new Metrics());
        int[] expected = new int[2000];
        Arrays.fill(expected, 3);
        assertArrayEquals(expected, a);
    }

    @Test
    void alreadySorted() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) a[i] = i;
        int[] expected = a.clone();
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    @Test
    void depthOnSortedArrayIsBoundedByTwoLogN() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(a, metrics);

        double log2n = Math.log(n) / Math.log(2);
        int bound = (int) Math.ceil(2 * log2n);

        assertTrue(metrics.getMaxDepth() <= bound,
                "maxDepth=" + metrics.getMaxDepth() + " should be <= 2*log2(n)=" + bound);
    }

    private static int[] randomArray(int size, Random rnd) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) a[i] = rnd.nextInt(2001) - 1000;
        return a;
    }
}
