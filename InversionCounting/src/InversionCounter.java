class InversionCounter {

    public static long countSwaps(int[] arr) {
        return counter(arr, 0, arr.length - 1);
    }

    public static long counter(int[] arr, int left, int right) {
        long inv = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            inv += counter(arr, left, mid)
                    + counter(arr, mid + 1, right)
                    + mergeAndCount(arr, left, mid, mid + 1, right);
            return inv;
        } else {
            return inv;
        }
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        long swaps = 0;
        int i = left1, j = left2;
        int[] sorted = new int[arr.length];

        while (i <= right1 && j <= right2) {
            if (arr[i] > arr[j]) {
                int temp = arr[j];
                for (int c = j; c > i; c--) {
                    arr[c] = arr[c - 1];
                }
                swaps += j - i;
                arr[i] = temp;
                i++;
                j++;
                right1++;
            } else {
                i++;
            }
        }
        return swaps;
    }
}
