import java.util.Arrays;

class Sorter {

    public static void sortStrings(String[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        sort(arr, 0, arr.length - 1);
    }

    public static void merge(String[] arr, int l, int m, int r) {
        String[] left = Arrays.copyOfRange(arr, l, m + 1);
        String[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (!isGreaterThan(left[i], right[j])) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    public static void sort(String[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static boolean isGreaterThan(String str1, String str2) {
        return str1.charAt(0) == str2.charAt(0)
                ? str1.charAt(1) > str2.charAt(1)
                : str1.charAt(0) > str2.charAt(0);
    }
}