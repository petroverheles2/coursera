import edu.princeton.cs.algs4.Digraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordNet {

    private final Set<String> allNouns = new HashSet<>();
    private final Map<Integer, String[]> synsetsMap = new HashMap<>();
    private Digraph wordNetDigraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        try {
            Scanner scanner = new Scanner(new File(synsets));
            while (scanner.hasNextLine()) {
                String[] lineElements = scanner.nextLine().split(",");
                Integer id = Integer.valueOf(lineElements[0]);
                String[] synset = lineElements[1].split(" ");
                synsetsMap.put(id, synset);
                for (int i = 0; i < synset.length; i++) {
                    allNouns.add(synset[i]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        wordNetDigraph = new Digraph(synsetsMap.size());
        try {
            Scanner scanner = new Scanner(new File(hypernyms));
            while (scanner.hasNextLine()) {
                String[] lineElements = scanner.nextLine().split(",");
                Integer synsetId = Integer.valueOf(lineElements[0]);
                for (int i = 1; i < lineElements.length; i++) {
                    wordNetDigraph.addEdge(synsetId, Integer.valueOf(lineElements[i]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return allNouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return allNouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}