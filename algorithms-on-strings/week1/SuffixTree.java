import java.util.*;
import java.io.*;

public class SuffixTree {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();

        if(!text.endsWith("$")) {
            text = text + "$";
        }

        List<Map<TrieEdge, Integer>> trie = buildSuffixTrie(text);

        List<Map<TreeEdge, Integer>> tree = new ArrayList<Map<TreeEdge, Integer>>();

        findEdges(text, trie, trie.get(0), result);

//        for (int i = 0; i < tree.size(); ++i) {
//            Map<TreeEdge, Integer> node = tree.get(i);
//            for (Map.Entry<TreeEdge, Integer> entry : node.entrySet()) {
//                result.add(text.substring(entry.getKey().position, entry.getKey().position + entry.getKey().length));
//            }
//        }

        return result;
    }

    public void findEdges(String text, List<Map<TrieEdge, Integer>> trie, Map<TrieEdge, Integer> node, List<String> result) {
        if(node.size() > 0) {
            for(Map.Entry<TrieEdge, Integer> entry : node.entrySet()) {
                int startingPosition = entry.getKey().position;
                LengthAndNextNodeIndex lengthAndNextNodeIndex = new LengthAndNextNodeIndex(1, entry.getValue());
                lengthAndNextNodeIndex = findLengthAndNextNodeIndex(lengthAndNextNodeIndex, trie);
                //result.add(text.substring(startingPosition, startingPosition + lengthAndNextNodeIndex.length));
                System.out.println(text.substring(startingPosition, startingPosition + lengthAndNextNodeIndex.length));
                findEdges(text, trie, trie.get(lengthAndNextNodeIndex.nodeIndex), result);
            }
        }
    }

    private LengthAndNextNodeIndex findLengthAndNextNodeIndex(LengthAndNextNodeIndex lengthAndNextNodeIndex, List<Map<TrieEdge, Integer>> trie) {
        if(trie.get(lengthAndNextNodeIndex.nodeIndex).size() == 0 || trie.get(lengthAndNextNodeIndex.nodeIndex).size() > 1) {
            return lengthAndNextNodeIndex;
        }
        lengthAndNextNodeIndex.length++;
        lengthAndNextNodeIndex.nodeIndex = trie.get(lengthAndNextNodeIndex.nodeIndex).entrySet().iterator().next().getValue();
        return findLengthAndNextNodeIndex(lengthAndNextNodeIndex, trie);
    }

    List<Map<TrieEdge, Integer>> buildSuffixTrie(String text) {
        List<Map<TrieEdge, Integer>> trie = new ArrayList<Map<TrieEdge, Integer>>();

        int nodeCounter = 0;
        trie.add(new HashMap<TrieEdge, Integer>());
        for(int i = 0; i < text.length(); i++) {
            TrieEdge currentSymbolEdge;
            int currentNode = 0;
            for(int k = i; k < text.length(); k++) {
                currentSymbolEdge = new TrieEdge(k, text.charAt(k));
                if(currentNode < trie.size() && trie.get(currentNode) != null && trie.get(currentNode).get(currentSymbolEdge) != null) {
                    currentNode = trie.get(currentNode).get(currentSymbolEdge);
                } else {
                    nodeCounter++;
                    int newNode = nodeCounter;
                    trie.get(currentNode).put(currentSymbolEdge, newNode);
                    currentNode = newNode;
                    if(currentNode >= trie.size()) {
                        trie.add(new HashMap<TrieEdge, Integer>());
                    }
                }
            }
        }

        return trie;
    }

    static class LengthAndNextNodeIndex {
        int length;
        int nodeIndex;

        public LengthAndNextNodeIndex(int length, int nodeIndex) {
            this.length = length;
            this.nodeIndex = nodeIndex;
        }
    }

    static class TrieEdge {
        int position;
        Character character;

        public TrieEdge(int position, Character character) {
            this.position = position;
            this.character = character;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TrieEdge trieEdge = (TrieEdge) o;

            return character.equals(trieEdge.character);

        }

        @Override
        public int hashCode() {
            return character.hashCode();
        }

        @Override
        public String toString() {
            return "(" + position +
                    ", " + character +
                    ")";
        }
    }

    static class TreeEdge {
        int position;
        int length;

        public TreeEdge(int position, int length) {
            this.position = position;
            this.length = length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TreeEdge treeEdge = (TreeEdge) o;

            if (position != treeEdge.position) return false;
            return length == treeEdge.length;

        }

        @Override
        public int hashCode() {
            int result = position;
            result = 31 * result + length;
            return result;
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        //print(edges);
    }
}
