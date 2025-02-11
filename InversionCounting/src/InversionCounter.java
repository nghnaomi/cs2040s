class InversionCounter {

    public static long countSwaps(int[] arr) {
        long swaps = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swaps++;
                }
            }
        }
        return swaps;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int swaps = 0;
        int i = left1;
        int j = left2;
        while (countSwaps(arr) != 0) {
            if (arr[i] > arr[j]) {
                int temp = arr[j];
                for (int c = j; c > i; c--) {
                    arr[c] = arr[c - 1];
                    swaps++;
                }
                arr[i] = temp;
                j++;
            } else {
                i++;
            }
        }
        return swaps;
    }
}
