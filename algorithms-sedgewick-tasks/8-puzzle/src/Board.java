import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[] flatBlocks;
    private final int dimension;
    private int hamming = -1;
    private int manhattan = -1;
    private boolean isGoalCalculated = false;
    private boolean isGoal = true;
    private Queue<Board> neighbors;
    private Board twin;

    private Board(int[] flatBlocks, int dimension) {
        this.flatBlocks = flatBlocks;
        this.dimension = dimension;
    }

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }

        if (blocks.length == 0 || blocks[0].length == 0) {
            throw new IllegalArgumentException("bad size, must be 2 <= n < 128");
        }

        if (blocks.length != blocks[0].length) {
            throw new IllegalArgumentException("number of rows and columns must be equal");
        }

        if (blocks.length < 2 || blocks.length >= 128) {
            throw new IllegalArgumentException("bad size, must be 2 <= n < 128");
        }

        this.dimension = blocks.length;

        this.flatBlocks = new int[dimension * dimension];
        final boolean[] validation = new boolean[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.flatBlocks[j + (i * dimension)] = blocks[i][j];
                if (validation[blocks[i][j]]) {
                    throw new IllegalArgumentException("Duplicated block: " + blocks[i][j]);
                } else {
                    validation[blocks[i][j]] = true;
                }
            }
        }
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == -1) {
            hamming = 0;
            for (int i = 0; i < flatBlocks.length; i++) {
                if (flatBlocks[i] == 0) {
                    continue;
                }

                if (flatBlocks[i] - 1 != i) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = 0;

            int currentRow;
            int currentColumn;
            int expectedRow;
            int expectedColumn;

            for (int i = 0; i < flatBlocks.length; i++) {
                if (flatBlocks[i] == 0) {
                    continue;
                }

                currentRow = i / dimension;
                currentColumn = i % dimension;
                expectedRow = (flatBlocks[i] - 1) / dimension;
                expectedColumn = (flatBlocks[i] - 1) % dimension;

                manhattan += Math.abs(currentRow - expectedRow) + Math.abs(currentColumn - expectedColumn);
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (isGoalCalculated) {
            return isGoal;
        }

        for (int i = 0; i < flatBlocks.length - 1; i++) {
            if (flatBlocks[i] - 1 != i) {
                isGoal = false;
                break;
            }
        }

        isGoalCalculated = true;

        return isGoal;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (twin == null) {
            int[] twinFlatBlocks = new int[flatBlocks.length];
            System.arraycopy(flatBlocks, 0, twinFlatBlocks, 0, flatBlocks.length);

            int firstBlockIndex;
            int secondBlockIndex;

            int randomIndex;
            do {
                randomIndex = StdRandom.uniform(twinFlatBlocks.length);
            } while (flatBlocks[randomIndex] == 0);

            firstBlockIndex = randomIndex;

            do {
                randomIndex = StdRandom.uniform(twinFlatBlocks.length);
            } while (flatBlocks[randomIndex] == 0 || randomIndex == firstBlockIndex);

            secondBlockIndex = randomIndex;

            int tmp = twinFlatBlocks[firstBlockIndex];
            twinFlatBlocks[firstBlockIndex] = twinFlatBlocks[secondBlockIndex];
            twinFlatBlocks[secondBlockIndex] = tmp;

            twin = new Board(twinFlatBlocks, dimension);
        }

        return twin;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.dimension != that.dimension) {
            return false;
        }

        for (int i = 0; i < flatBlocks.length; i++) {
            if (this.flatBlocks[i] != that.flatBlocks[i]) {
                return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors != null) {
            return neighbors;
        }

        neighbors = new Queue<>();

        // find index of emptyBlock;
        int emptyBlockIndex = -1;
        for (int i = 0; i < flatBlocks.length; i++) {
            if (flatBlocks[i] == 0) {
                emptyBlockIndex = i;
                break;
            }
        }

        int emptyBlockRow = emptyBlockIndex / dimension;
        int emptyBlockColumn = emptyBlockIndex % dimension;

        if (emptyBlockColumn > 0) { // has block on the left of empty block
            int[] flatBlocksCopy = new int[flatBlocks.length];
            System.arraycopy(flatBlocks, 0, flatBlocksCopy, 0, flatBlocks.length);
            flatBlocksCopy[emptyBlockIndex] = flatBlocksCopy[emptyBlockIndex - 1];
            flatBlocksCopy[emptyBlockIndex - 1] = 0;
            Board neighbor = new Board(flatBlocksCopy, dimension);
            neighbors.enqueue(neighbor);
        }

        if (emptyBlockColumn < dimension - 1) { // has block on the right of empty block
            int[] flatBlocksCopy = new int[flatBlocks.length];
            System.arraycopy(flatBlocks, 0, flatBlocksCopy, 0, flatBlocks.length);
            flatBlocksCopy[emptyBlockIndex] = flatBlocksCopy[emptyBlockIndex + 1];
            flatBlocksCopy[emptyBlockIndex + 1] = 0;
            Board neighbor = new Board(flatBlocksCopy, dimension);
            neighbors.enqueue(neighbor);
        }

        if (emptyBlockRow > 0) { // has block on the top of empty block
            int[] flatBlocksCopy = new int[flatBlocks.length];
            System.arraycopy(flatBlocks, 0, flatBlocksCopy, 0, flatBlocks.length);
            flatBlocksCopy[emptyBlockIndex] = flatBlocksCopy[emptyBlockIndex - dimension];
            flatBlocksCopy[emptyBlockIndex - dimension] = 0;
            Board neighbor = new Board(flatBlocksCopy, dimension);
            neighbors.enqueue(neighbor);
        }

        if (emptyBlockRow < dimension - 1) { // has block on the bottom of empty block
            int[] flatBlocksCopy = new int[flatBlocks.length];
            System.arraycopy(flatBlocks, 0, flatBlocksCopy, 0, flatBlocks.length);
            flatBlocksCopy[emptyBlockIndex] = flatBlocksCopy[emptyBlockIndex + dimension];
            flatBlocksCopy[emptyBlockIndex + dimension] = 0;
            Board neighbor = new Board(flatBlocksCopy, dimension);
            neighbors.enqueue(neighbor);
        }

        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(dimension);
        res.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                res.append(String.format("%2d ", flatBlocks[i * dimension + j]));
            }
            res.append("\n");
        }
        return res.toString();
    }
}
