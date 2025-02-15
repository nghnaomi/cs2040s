import java.util.Random;

public class IdentifySort {
    public static long[] findArrays (ISort sorter){
        Random random = new Random(2);

        // sorted array
        KeyValuePair[] sorted = new KeyValuePair[5000];
        for (int i = 0; i < 5000; i++) {
            sorted[i] = new KeyValuePair(i, 1);
        }

        // reversed array
        KeyValuePair[] reversed = new KeyValuePair[5000];
        for (int i = 0; i < 5000; i++) {
            reversed[i] = new KeyValuePair(5000 - i, 1);
        }

        // average array
        KeyValuePair[] avg = new KeyValuePair[5000];
        for (int i = 0; i < 5000; i++) {
            avg[i] = new KeyValuePair(random.nextInt(), 1);
        }

        // duplicates array
        KeyValuePair[] dup = new KeyValuePair[5000];
        for (int i = 0; i < 1000; i++) {
            dup[i] = new KeyValuePair(3, 1);
        }
        for (int i = 10; i < 2000; i++) {
            dup[i] = new KeyValuePair(1, 1);
        }
        for (int i = 20; i < 3000; i++) {
            dup[i] = new KeyValuePair(4, 1);
        }
        for (int i = 30; i < 4000; i++) {
            dup[i] = new KeyValuePair(2, 1);
        }
        for (int i = 40; i < 5000; i++) {
            dup[i] = new KeyValuePair(5, 1);
        }

        // negative array
        KeyValuePair[] negative = new KeyValuePair[5000];
        for (int i = 0; i < 5000; i++) {
            negative[i] = new KeyValuePair(0 - i, 1);
        }

        long[] costs = new long[11];

        // check each one is sorted and return cost otherwise return -1;
        long sortedStart = System.nanoTime();
        long costSorted = sorter.sort(sorted);
        long sortedEnd = System.nanoTime();
        costs[0] = isSorted(sorted) ? costSorted : -1;
        costs[5] = sortedEnd - sortedStart;

        long revStart = System.nanoTime();
        long costRev = sorter.sort(reversed);
        long revEnd = System.nanoTime();
        costs[1] = isSorted(reversed) ? costRev : -1;
        costs[6] = revEnd - revStart;

        long avgStart = System.nanoTime();
        long costAvg = sorter.sort(avg);
        long avgEnd = System.nanoTime();
        costs[2] = isSorted(avg) ? costAvg : -1;
        costs[7] = avgEnd - avgStart;

        long dupStart = System.nanoTime();
        long costDup = sorter.sort(dup);
        long dupEnd = System.nanoTime();
        costs[3] = isSorted(dup) ? costDup : -1;
        costs[8] = dupEnd - dupStart;

        long negStart = System.nanoTime();
        long costNeg = sorter.sort(negative);
        long negEnd = System.nanoTime();
        costs[4] = isSorted(negative) ? costNeg : -1;
        costs[9] = negEnd - negStart;

        costs[10] = SortingTester.isStable(sorter, 50) ? 1 : 0;

        return costs;
    }

    public static boolean isSorted(KeyValuePair[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].getKey() < array[i - 1].getKey()) {
                return false;
            }
        }
        return true;
    }
}