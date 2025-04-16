import java.util.*;

public class Solution {
    private TreeSet<Node> scores;
    private Map<Integer, Node> teamToScore;
    private Comparator<Node> comparator;

    static class Node {
        int team;
        int solved;
        long penalty;

        Node(int team, int solved, long penalty) {
            this.team = team;
            this.solved = solved;
            this.penalty = penalty;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return team == node.team && solved == node.solved && penalty == node.penalty;
        }

        @Override
        public int hashCode() {
            return Objects.hash(team, solved, penalty);
        }
    }

    public Solution(int numTeams) {
        this.comparator = (a, b) -> {
            if (a.solved != b.solved) {
                return Integer.compare(b.solved, a.solved);
            } else if (a.penalty != b.penalty) {
                return Long.compare(a.penalty, b.penalty);
            } else {
                return Integer.compare(a.team, b.team);
            }
        };
        this.scores = new TreeSet<>(comparator);
        this.teamToScore = new HashMap<>();
        for (int team = 1; team <= numTeams; team++) {
            Node ts = new Node(team, 0, 0);
            teamToScore.put(team, ts);
            scores.add(ts);
        }
    }

    public int update(int team, long newPenalty) {
        Node old = teamToScore.get(team);
        scores.remove(old);
        Node updated = new Node(team, old.solved + 1, old.penalty + newPenalty);
        teamToScore.put(team, updated);
        scores.add(updated);

        Node team1 = teamToScore.get(1);
        return scores.headSet(team1, false).size() + 1;
    }
}