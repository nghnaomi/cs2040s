import org.junit.Test;

import static org.junit.Assert.*;

public class InversionCounterTest {

    @Test
    public void countSwapsTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest3() {
        int[] arr = {5, 4, 3, 2, 1};
        assertEquals(10L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest4() {
        int[] arr = {2, 0, 3, 4, 3, 1};
        assertEquals(6L, InversionCounter.countSwaps(arr));
    }


    @Test
    public void mergeAndCountTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.mergeAndCount(arr, 0, 0, 1, 2));
    }

    @Test
    public void mergeAndCountTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.mergeAndCount(arr, 0, 2, 3, 3));
    }

    @Test
    public void mergeAndCountTest3() {
        int[] arr = {2, 4, 7, 0, 1, 5, 6};
        assertEquals(8L, InversionCounter.mergeAndCount(arr, 0, 2, 3, 6));
    }

    @Test
    public void mergeAndCountTest4() {
        int[] arr = {2, 2, 2, 2, 1, 2, 3};
        assertEquals(4L, InversionCounter.mergeAndCount(arr, 0, 3, 4, 6));
    }
}