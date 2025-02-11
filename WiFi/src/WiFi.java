import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        if (houses.length == 0 || numOfAccessPoints < 0) {
            return 0.0;
        } else if (houses.length < numOfAccessPoints) {
            return 0.0;
        } else {
            Arrays.sort(houses);
            double low = 0.0;
            double high = houses[houses.length - 1];
            double dis = -1.0;

            while (low <= high) {
                double mid = low + (high - low) / 2;
                if (coverable(houses, numOfAccessPoints, mid)) {
                    dis = mid;
                    high = mid - 0.05;
                } else {
                    low = mid + 0.05;
                }
            }

            return dis;
        }
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        if (houses.length <= numOfAccessPoints) {
            return true;
        } else {
            int routers_used = 1, curr = 0;
            double pos = houses[0] + distance;

            for (int house : houses) {
                if (house > pos + distance) {
                    routers_used++;
                    if (routers_used > numOfAccessPoints) {
                        return false;
                    }
                    pos = house + distance;
                }
            }
            return true;
        }

    }
}