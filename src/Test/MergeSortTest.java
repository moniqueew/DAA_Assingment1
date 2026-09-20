import Algorithms.MergeSort;
import Metrics.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 16, 50, 200, 1000})
    void matchesArraysSortOnRandomArrays(int size) {
        Random rnd = new Random(size + 1);
        for (int trial = 0; trial < 15; trial++) { // 7 sizes * 15 = 105 random arrays
            int[] a = randomArray(size, rnd);
            int[] expected = a.clone();
            Arrays.sort(expected);

            MergeSort.sort(a, new Metrics());
            assertArrayEquals(expected, a, "size=" + size + " trial=" + trial);
        }
    }

    @Test
    void emptyArray() {
        int[] a = {};
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void singleElement() {
        int[] a = {42};
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[500];
        Arrays.fill(a, 7);
        MergeSort.sort(a, new Metrics());
        int[] expected = new int[500];
        Arrays.fill(expected, 7);
        assertArrayEquals(expected, a);
    }

    @Test
    void alreadySorted() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) a[i] = i;
        int[] expected = a.clone();
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    private static int[] randomArray(int size, Random rnd) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) a[i] = rnd.nextInt(2001) - 1000;
        return a;
    }
}
