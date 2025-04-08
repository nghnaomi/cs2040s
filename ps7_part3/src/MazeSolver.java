import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;

	public MazeSolver() {
		this.maze = maze;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) {
		if (maze == null) {
			return null;
		}

		int rows = maze.getRows();
		int cols = maze.getColumns();

		if (startRow < 0 || startCol < 0 || startRow >= rows || startCol >= cols ||
				endRow < 0 || endCol < 0 || endRow >= rows || endCol >= cols) {
			return null;
		}

		PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

		int[][] minFear = new int[rows][cols];
		for (int[] row : minFear) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}

		minFear[startRow][startCol] = 0;
		pq.offer(new int[]{startRow, startCol, 0});

		while (!pq.isEmpty()) {
			int[] curr = pq.poll();
			int row = curr[0];
			int col = curr[1];
			int fear = curr[2];

			if (row == endRow && col == endCol) {
				return fear;
			} else if (fear > minFear[row][col]) {
				continue;
			}
			
			for (int direction = 0; direction < 4; direction++) {
				int newRow = row + DELTAS[direction][0];
				int newCol = col + DELTAS[direction][1];
				
				if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
					int wallValue = WALL_FUNCTIONS.get(direction).apply(maze.getRoom(row, col));

					if (wallValue == TRUE_WALL) {
						continue;
					}

					int fearIncrement = wallValue == EMPTY_SPACE ? 1 : wallValue;
					int newFear = fear + fearIncrement;

					if (newFear < minFear[newRow][newCol]) {
						minFear[newRow][newCol] = newFear;
						pq.offer(new int[]{newRow, newCol, newFear});
					}
				}
			}
		}
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		return null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 0, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
