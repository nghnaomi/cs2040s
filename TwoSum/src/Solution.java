import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr, int target) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        int count = 0;

        for (int num : arr) {
            int remainder = target - num;
            if (freqMap.containsKey(remainder) && freqMap.get(remainder) > 0) {
                freqMap.replace(remainder, freqMap.get(remainder) - 1);
                count++;
            } else {
                if (freqMap.containsKey(num)) {
                    freqMap.replace(num, freqMap.get(num) + 1);
                } else {
                    freqMap.put(num, 1);
                }
            }
        }

        return count;
    }
}
