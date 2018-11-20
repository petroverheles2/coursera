import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private final Iterable<Board> solution;
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Comparator<SearchNode> comparator = new SearchNodeComparator();

        MinPQ<SearchNode> mainPQ = new MinPQ<>(comparator);
        mainPQ.insert(new SearchNode(initial, null, 0));

        MinPQ<SearchNode> twinPQ = new MinPQ<>(comparator);
        twinPQ.insert(new SearchNode(initial.twin(), null, 0));

        while (true) {
            SearchNode mainSearchNode = mainPQ.delMin();
            if (mainSearchNode.board.isGoal()) {
                solution = extractSolution(mainSearchNode);
                moves = mainSearchNode.numberOfMoves;
                break;
            } else {
                for (Board neighbor : mainSearchNode.board.neighbors()) {
                    if (mainSearchNode.predecessor == null ||
                            !mainSearchNode.predecessor.board.equals(neighbor)) {
                        SearchNode neighborSearchNode = new SearchNode(neighbor, mainSearchNode, mainSearchNode.numberOfMoves + 1);
                        mainPQ.insert(neighborSearchNode);
                    }
                }
            }

            SearchNode twinSearchNode = twinPQ.delMin();
            if (twinSearchNode.board.isGoal()) {
                solution = null;
                moves = -1;
                break;
            } else {
                for (Board neighbor : twinSearchNode.board.neighbors()) {
                    if (twinSearchNode.predecessor == null || !twinSearchNode.predecessor.board.equals(neighbor)) {
                        SearchNode neighborSearchNode = new SearchNode(neighbor, twinSearchNode, twinSearchNode.numberOfMoves + 1);
                        twinPQ.insert(neighborSearchNode);
                    }
                }
            }
        }
    }

    private Iterable<Board> extractSolution(SearchNode searchNode) {
        Stack<Board> steps = new Stack<>();
        while (searchNode != null) {
            steps.push(searchNode.board);
            searchNode = searchNode.predecessor;
        }
        return steps;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class SearchNode {
        Board board;
        SearchNode predecessor;
        int numberOfMoves;

        SearchNode(Board board, SearchNode predecessor, int numberOfMoves) {
            this.board = board;
            this.predecessor = predecessor;
            this.numberOfMoves = numberOfMoves;
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode s1, SearchNode s2) {
            return (s1.board.manhattan() + s1.numberOfMoves) - (s2.board.manhattan() + s2.numberOfMoves);
        }
    }
}
