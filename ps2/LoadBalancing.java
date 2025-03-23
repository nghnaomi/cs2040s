/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        // TODO: Implement this
        int processors = 1, load = 0;

        for (int job : jobSizes) {
            if (job > queryLoad) {
                return false;
            } else if (load + job > queryLoad) {
                processors++;
                if (processors > p) {
                    return false;
                }
                load = job;
            } else {
                load += job;
            }
        }
        return true;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        // TODO: Implement this
        if (jobSizes.length == 0 || p < 0) {
            return -1;
        } else if (jobSizes.length < p) {
            int max_load = Integer.MIN_VALUE;
            for (int job : jobSizes) {
                if (job > max_load) {
                    max_load = job;
                }
            }
            return max_load;
        } else {
            int low = 0, high = 0, max_load = -1;

            for (int job : jobSizes) {
                if (job > low) {
                    low = job;
                }
            }

            for (int job : jobSizes) {
                high += job;
            }

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (isFeasibleLoad(jobSizes, mid, p)) {
                    max_load = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            return max_load;
        }
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
