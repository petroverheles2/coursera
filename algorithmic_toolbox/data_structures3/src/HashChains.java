import java.io.*;
import java.util.*;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    private List<String>[] chains;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        int hash;
        List<String> chain;
        switch (query.type) {
            case "add":
                hash = hashFunc(query.s);
                chain = chains[hash];
                if(chain == null) {
                    chain = new LinkedList<>();
                    chains[hash] = chain;
                }

                if (!chain.contains(query.s)) {
                    chain.add(0, query.s);
                }
                break;
            case "del":
                hash = hashFunc(query.s);
                chain = chains[hash];
                if(chain != null) {
                    chain.remove(query.s);
                }
                break;
            case "find":
                hash = hashFunc(query.s);
                chain = chains[hash];
                writeSearchResult(chain != null && chain.contains(query.s));
                break;
            case "check":
                chain = chains[query.ind];
                if(chain != null && !chain.isEmpty()) {
                    for (String cur : chain) {
                        out.print(cur + " ");
                    }
                }

                out.println();

                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        chains = new LinkedList[bucketCount];
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
