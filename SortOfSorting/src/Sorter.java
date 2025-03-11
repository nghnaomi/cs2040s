import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;

class Sorter {

    public static void sortStrings(String[] arr) {
        if (arr.length > 2) {
            sort(arr, 0, arr.length - 1);
        }
    }

    public static void merge(String[] arr, int l, int m, int r) {
        int i = l, j = m + 1, k = 0;
        String[] temp = new String[r - l + 1];
        for (int x = 0; x < temp.length; x++) {
            temp[x] = arr[l + x];
        }

        while (i <= m && j <= r) {
            if (isGreaterThan(temp[i - l], temp[j - l])) {
                arr[l + k] = temp[j - l];
                j++;
            } else {
                arr[l + k] = temp[i - l];
                i++;
            }
            k++;
        }

        while (i <= m) {
            arr[l + k] = temp[i - l];
            i++;
            k++;
        }

        while (j <= r) {
            arr[l + k] = temp[j - l];
            j++;
            k++;
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
        if (str1.charAt(0) != str2.charAt(0)) {
            return str1.charAt(0) > str2.charAt(0);
        }
        if (str1.length() == 1 || str2.length() == 1) {
            return str1.length() > str2.length();
        }
        return str1.charAt(1) > str2.charAt(1);
    }
}
