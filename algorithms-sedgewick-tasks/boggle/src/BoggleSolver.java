import edu.princeton.cs.algs4.TST;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver
{

    private final TST<Boolean> dictionaryWords = new TST<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            dictionaryWords.put(word, true);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        Set<String> result = new HashSet<>();

        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                WordPath wordPath = new WordPath(board.rows(), board.cols());
                explore(row, col, board, wordPath, result);
            }
        }

        return result;
    }

    private void explore(int row, int col, BoggleBoard board, WordPath wordPath, Set<String> result) {
        if (wordPath.containsCell(row, col)) {
            return;
        }

        if (wordPath.getWordLength() > 2) {
            String word = wordPath.getWord();

            if (!dictionaryWords.keysWithPrefix(word).iterator().hasNext()) {
                return;
            }

            if (dictionaryWords.contains(word)) {
                result.add(word);
            }
        }

        // explore bottom neighbour
        WordPath newWordPath = new WordPath(wordPath, board, row, col);

        if (row < board.rows() - 1) {
            explore(row + 1, col, board, newWordPath, result);
        }

        // explore top neighbour
        if (row != 0) {
            explore(row - 1, col, board, newWordPath, result);
        }

        // explore right neighbour
        if (col < board.cols() - 1) {
            explore(row, col + 1, board, newWordPath, result);
        }

        // explore left neighbour
        if (col != 0) {
            explore(row, col - 1, board, newWordPath, result);
        }

        // explore bottom right neighbour
        if (row < board.rows() - 1 && col < board.cols() - 1) {
            explore(row + 1, col + 1, board, newWordPath, result);
        }

        // explore top left neighbour
        if (row != 0 && col != 0) {
            explore(row - 1, col - 1, board, newWordPath, result);
        }

        // explore bottom left neighbour
        if (row < board.rows() - 1 && col != 0) {
            explore(row + 1, col - 1, board, newWordPath, result);
        }

        // explore top right neighbour
        if (row != 0 && col < board.cols() - 1) {
            explore(row - 1, col + 1, board, newWordPath, result);
        }

        // don't forget to check the word if we're stuck on boundary
        String newWord = newWordPath.getWord();
        if (newWord.length() > 2 && dictionaryWords.contains(newWord)) {
            result.add(newWord);
        }

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int length = word.length();

        if (word.length() < 3) {
            return 0;
        }

        if (!dictionaryWords.contains(word)) {
            return 0;
        }

        switch (length) {
            case 3:
            case 4: return 1;
            case 5: return 2;
            case 6: return 3;
            case 7: return 5;
            default: return 11;
        }
    }

    private static class WordPath {
        private final boolean[][] visited;
        private final StringBuilder wordBuilder;

        WordPath(int rows, int cols) {
            visited = new boolean[rows][cols];
            wordBuilder = new StringBuilder();
        }

        WordPath(WordPath wordPath, BoggleBoard board, int row, int col) {
            visited = cloneVisited(wordPath.visited);
            wordBuilder = new StringBuilder(wordPath.wordBuilder);
            visited[row][col] = true;
            char letter = board.getLetter(row, col);
            wordBuilder.append(letter);
            if (letter == 'Q') {
                wordBuilder.append('U');
            }
        }

        boolean containsCell(int row, int col) {
            return visited[row][col];
        }

        String getWord() {
            return wordBuilder.toString();
        }

        int getWordLength() {
            return wordBuilder.length();
        }

        private boolean[][] cloneVisited(boolean[][] src) {
            int length = src.length;
            boolean[][] target = new boolean[length][src[0].length];
            for (int i = 0; i < length; i++) {
                System.arraycopy(src[i], 0, target[i], 0, src[i].length);
            }
            return target;
        }
    }
}