import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {

    private final Set<String> allNouns = new HashSet<>();
    private final Map<Integer, String> synsetsMap = new HashMap<>();
    private final Map<String, Integer> nounToSynsetIdMap = new HashMap<>();
    private Digraph wordNetDigraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In synsetsIO = new In(synsets);
        while (synsetsIO.hasNextLine()) {
            String[] lineElements = synsetsIO.readLine().split(",");
            int synsetId = Integer.parseInt(lineElements[0]);
            String synset = lineElements[1];
            synsetsMap.put(synsetId, synset);
            List<String> synsetNouns = Arrays.asList(synset.split(" "));
            allNouns.addAll(synsetNouns);
            for (String noun : synsetNouns) {
                nounToSynsetIdMap.put(noun, synsetId);
            }
        }

        wordNetDigraph = new Digraph(synsetsMap.size());

        In hypernymsIO = new In(hypernyms);
        while (hypernymsIO.hasNextLine()) {
            String[] lineElements = hypernymsIO.readLine().split(",");
            int synsetId = Integer.parseInt(lineElements[0]);
            for (int i = 1; i < lineElements.length; i++) {
                wordNetDigraph.addEdge(synsetId, Integer.parseInt(lineElements[i]));
            }
        }
        hypernymsIO.close();
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
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int vertexA = nounToSynsetIdMap.get(nounA);
        int vertexB = nounToSynsetIdMap.get(nounB);

        BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(wordNetDigraph, vertexA);
        return paths.distTo(vertexB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int vertexA = nounToSynsetIdMap.get(nounA);
        int vertexB = nounToSynsetIdMap.get(nounB);
        BreadthFirstDirectedPaths pathsA = new BreadthFirstDirectedPaths(this.wordNetDigraph, vertexA);
        BreadthFirstDirectedPaths pathsB = new BreadthFirstDirectedPaths(this.wordNetDigraph, vertexB);

        int sapLength = Integer.MAX_VALUE;
        int sapVertex = -1;
        for (int synsetId : synsetsMap.keySet()) {
            if (pathsA.hasPathTo(synsetId) && pathsB.hasPathTo(synsetId)) {
                int sapCandidateLength = pathsA.distTo(synsetId) + pathsB.distTo(synsetId);
                if (sapCandidateLength < sapLength) {
                    sapLength = sapCandidateLength;
                    sapVertex = synsetId;
                }
            }
        }

        return synsetsMap.get(sapVertex);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        //System.out.println(wordNet.wordNetGraph);
        System.out.println(wordNet.distance("Coumadin", "Accipitridae"));
        System.out.println(wordNet.sap("Coumadin", "Accipitridae"));
        StdOut.println(wordNet.sap("European_magpie", "brace_wrench"));
    }
}