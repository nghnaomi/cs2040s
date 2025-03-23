import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private int rows;
	private int cols;
	private boolean solved = false;
	private boolean[][] visited;
	private int[][] matrix;
	private int[][] steps;
	private int endRow, endCol;

	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.rows = maze.getRows();
		this.cols = maze.getColumns();
		visited = new boolean[rows][cols];
		matrix = new int[rows * cols][rows * cols];
		steps = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				visited[i][j] = false;
				steps[i][j] = Integer.MAX_VALUE;
			}
		}
		solved = false;

		fillMatrix();
	}

	public void fillMatrix() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int curr = row * cols + col;
				for (int dir = 0; dir < 4; dir++) {
					boolean haswall = false;

					switch (dir) {
						case NORTH:
							haswall = maze.getRoom(row, col).hasNorthWall();
							break;
						case SOUTH:
							haswall = maze.getRoom(row, col).hasSouthWall();
							break;
						case EAST:
							haswall = maze.getRoom(row, col).hasEastWall();
							break;
						case WEST:
							haswall = maze.getRoom(row, col).hasWestWall();
							break;
					}

					if (!haswall) {
						int nextRow = row + DELTAS[dir][0];
						int nextCol = col + DELTAS[dir][1];
						if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols) {
							int nextRoom = nextRow * cols + nextCol;
							if (matrix[curr][nextRoom] == 0) {
								matrix[curr][nextRoom] = 1;
								matrix[nextRoom][curr] = 1;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		solved = false;

		if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols ||
				endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
			throw new IllegalArgumentException("invalid start/end");
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				visited[i][j] = false;
				steps[i][j] = Integer.MAX_VALUE;
			}
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] { startRow, startCol });
		visited[startRow][startCol] = true;
		steps[startRow][startCol] = 0;
		int ans = 0;

		Map<String, String> parent = new HashMap<>();
		parent.put(startRow + "," + startCol, null);

		while (!queue.isEmpty()) {
			int[] present = queue.poll();
			int row = present[0], col = present[1];
			int curr = row * cols + col;

			if (row == endRow && col == endCol) {
				solved = true;
				maze.getRoom(startRow, startCol).onPath = true;
				ans = tracePath(parent, startRow, startCol, endRow, endCol);
			}

			for (int dir = 0; dir < 4; dir++) {
				int nextRow = row + DELTAS[dir][0];
				int nextCol = col + DELTAS[dir][1];

				if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols) {
					int nextRoom = nextRow * cols + nextCol;

					if (matrix[curr][nextRoom] == 1 && !visited[nextRow][nextCol]) {
						visited[nextRow][nextCol] = true;
						steps[nextRow][nextCol] = steps[row][col] + 1;
						queue.add(new int[]{nextRow, nextCol});
						parent.put(nextRow + "," + nextCol, row + "," + col);
					}
				}
			}
		}
		return solved ? ans : null;
	}

	private int tracePath(Map<String, String> parent, int startRow, int startCol, int endRow, int endCol) {
		String curr = endRow + "," + endCol;
		int steps = 0;
		while (curr != null) {
			steps++;
			String[] splitCurr = curr.split(",");
			int row = Integer.parseInt(splitCurr[0]);
			int col = Integer.parseInt(splitCurr[1]);
			maze.getRoom(row, col).onPath = true;
			curr = parent.get(curr);
		}
		return steps - 1;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k < 0 || k > rows * cols) {
			throw new IllegalArgumentException("k must be positive");
		}

		int count = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (steps[i][j] == k) {
					count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
