import java.util.Arrays;

class InversionCounter {

    public static long countSwaps(int[] arr) {
        return (arr.length < 2 || arr == null)
                ? 0
                : counter(arr, 0, arr.length - 1);
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
        int i = 0, j = 0, c = left1;

        int[] left = Arrays.copyOfRange(arr, left1, right1 + 1);
        int[] right = Arrays.copyOfRange(arr, left2, right2 + 1);

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[c++] = left[i++];
            } else {
                swaps += (left.length - i);
                arr[c++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[c++] = left[i++];
        }

        while (j < right.length) {
            arr[c++] = right[j++];
        }
        return swaps;
    }

}

