import Algorithms.QuickSelect;
import Metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickSelectTest {

    @Test
    void matchesSortedArrayOnRandomArrays() {
        Random rnd = new Random(123);
        for (int trial = 0; trial < 100; trial++) {
            int size = 1 + rnd.nextInt(500);
            int[] a = new int[size];
            for (int i = 0; i < size; i++) a[i] = rnd.nextInt(2001) - 1000;

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(size);
            int[] copy = a.clone();
            int result = QuickSelect.select(copy, k, new Metrics());

            assertEquals(sorted[k], result, "size=" + size + " k=" + k + " trial=" + trial);
        }
    }

    @Test
    void singleElement() {
        int[] a = {99};
        assertEquals(99, QuickSelect.select(a, 0, new Metrics()));
    }

    @Test
    void allElementsEqual() {
        int[] a = new int[100];
        Arrays.fill(a, 5);
        assertEquals(5, QuickSelect.select(a, 37, new Metrics()));
    }

    @Test
    void alreadySortedArray() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertEquals(4, QuickSelect.select(a.clone(), 3, new Metrics()));
        assertEquals(1, QuickSelect.select(a.clone(), 0, new Metrics()));
        assertEquals(10, QuickSelect.select(a.clone(), 9, new Metrics()));
    }

    @Test
    void emptyArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0, new Metrics()));
    }

    @Test
    void nullArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(null, 0, new Metrics()));
    }

    @Test
    void negativeKThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, new Metrics()));
    }

    @Test
    void tooLargeKThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, new Metrics()));
    }
}
