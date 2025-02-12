import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.Arrays;
import java.util.Random;

public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        Random random = new Random();
        KeyValuePair[] array = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            array[i] = new KeyValuePair(random.nextInt(), i);
        }

        sorter.sort(array);

        for (int i = 1; i < size; i++) {
            if (array[i].getKey() < array[i - 1].getKey()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        if (size == 0 || size == 1) {
            return true;
        } else {
            Random random = new Random();
            KeyValuePair[] array = new KeyValuePair[size];
            for (int i = 0; i < size; i++) {
                array[i] = new KeyValuePair(random.nextInt(size / 2), i);
            }

            sorter.sort(array);

            for (int i = 1; i < size; i++) {
                if (array[i].getKey() == array[i - 1].getKey()
                        && array[i].getValue() < array[i - 1].getValue()) {
                    return false;
                } else if (array[i].getKey() < array[i - 1].getKey()) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("SorterA: " + Arrays.toString(IdentifySort.findArrays(new SorterA())));
        System.out.println("SorterB: " + Arrays.toString(IdentifySort.findArrays(new SorterB())));
        System.out.println("SorterC: " + Arrays.toString(IdentifySort.findArrays(new SorterC())));
        System.out.println("SorterD: " + Arrays.toString(IdentifySort.findArrays(new SorterD())));
        System.out.println("SorterE: " + Arrays.toString(IdentifySort.findArrays(new SorterE())));
        System.out.println("SorterF: " + Arrays.toString(IdentifySort.findArrays(new SorterF())));
    }
}
