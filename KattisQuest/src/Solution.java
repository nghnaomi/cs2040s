import java.util.*;

public class Solution {
    private TreeMap<Long, PriorityQueue<Long>> energyToGold;

    public Solution() {
        energyToGold = new TreeMap<>();
    }

    void add(long energy, long value) {
        if (!energyToGold.containsKey(energy)) {
            PriorityQueue<Long> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            maxHeap.add(value);
            energyToGold.put(energy, maxHeap);
        } else {
            PriorityQueue<Long> existingHeap = energyToGold.get(energy);
            existingHeap.add(value);
        }
    }

    long query(long remainingEnergy) {
        long totalGold = 0;
        while (remainingEnergy > 0) {
            Long floorKey = energyToGold.floorKey(remainingEnergy);

            if (floorKey == null) {
                break;
            }

            PriorityQueue<Long> values = energyToGold.get(floorKey);
            long value = values.poll();
            totalGold += value;
            remainingEnergy -= floorKey;

            if (values.isEmpty()) {
                energyToGold.remove(floorKey);
            }
        }
        return totalGold;
    }
}