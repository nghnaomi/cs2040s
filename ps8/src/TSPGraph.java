import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        if (map.getCount() == 0) return;

        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        pq.add(0,0.0);

        int[] parent = new int[map.getCount()];
        boolean[] visited = new boolean[map.getCount()];
        for (int i = 0; i < map.getCount(); i++) {
            parent[i] = -1;
            visited[i] = false;
        }

        while (!pq.isEmpty()) {
            int curr = pq.extractMin();
            if(visited[curr]) continue;
            visited[curr] = true;

            for (int v = 0; v < map.getCount(); v++) {
                if (v != curr && !visited[v]) {
                    double distance = map.pointDistance(curr, v);
                    Double minWeight = pq.lookup(v);

                    if (minWeight == null) {
                        parent[v] = curr;
                        pq.add(v, distance);
                    } else if (distance < minWeight) {
                        parent[v] = curr;
                        pq.decreasePriority(v, distance);
                    }
                }
            }
        }

        for (int v = 0; v < map.getCount(); v++) {
            if (parent[v] != -1) {
                map.setLink(v, parent[v], false);
            }
        }
        map.redraw();
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);

        boolean[] visited = new boolean[map.getCount()];
        List<Integer> tourOrder = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(0);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (visited[current]) continue;
            visited[current] = true;
            tourOrder.add(current);

            for (int neighbor = 0; neighbor < map.getCount(); neighbor++) {
                if ((map.getLink(current) == neighbor || map.getLink(neighbor) == current)
                        && !visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }

        for (int i = 0; i < tourOrder.size()-1; i++) {
            map.setLink(tourOrder.get(i), tourOrder.get(i + 1), false);
        }
        map.setLink(tourOrder.get(tourOrder.size() - 1), tourOrder.get(0), false);
        map.redraw();
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        boolean visited[] = new boolean[map.getCount()];
        int curr = 0;
        int count = 0;

        while (true) {
            if (visited[curr]) {
                return curr == 0 && count == map.getCount();
            }

            visited[curr] = true;
            count++;
            int next = map.getLink(curr);
            curr = next;
        }
    }

    @Override
    public double tourDistance(TSPMap map) {
        if (!isValidTour(map)) return -1;

        double totalDistance = 0.0;
        int curr = 0;
        int start = curr;

        do {
            int next = map.getLink(curr);
            totalDistance += map.pointDistance(curr, next);
            curr = next;
        } while (curr != start);

        return totalDistance;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        // graph.MST(map);
        graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        // System.out.println(graph.tourDistance(map));
    }
}
