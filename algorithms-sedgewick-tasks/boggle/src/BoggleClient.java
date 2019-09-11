import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleClient {
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        long start = System.currentTimeMillis();
        Iterable<String> allValidWords = solver.getAllValidWords(board);
        long end = System.currentTimeMillis();
        int count = 0;
        for (String word : allValidWords) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("All valid words count: " + count);
        StdOut.println("Score = " + score);
        StdOut.println("Solved for " + (end - start) + " milliseconds");
    }

}
