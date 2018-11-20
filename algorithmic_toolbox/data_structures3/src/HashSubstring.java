import java.io.*;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    public static List<Integer> getOccurrences(Data input) {
        int p = Integer.MAX_VALUE;
        int x = new Random().nextInt(p - 2) + 1;

        //System.out.println(x);

        String pattern = input.pattern, text = input.text;

        int patternHash = polyHash(pattern, p, x);
        int h[] = precomputeHashes(text, pattern, p, x);

        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i <= text.length() - pattern.length(); ++i) {
            if(patternHash != h[i]) {
                continue;
            }

            String substring = text.substring(i, i + pattern.length());
            if(substring.equals(pattern)) {
                occurrences.add(i);
            }
	    }
        return occurrences;
    }

    private static int[] precomputeHashes(String text, String pattern, int p, int x) {
        int[] h = new int[text.length() - pattern.length() + 1];
        String s = text.substring(text.length() - pattern.length(), text.length());
        h[text.length() - pattern.length()] = polyHash(s, p, x);

        int y = 1;

        for(int i = 0; i < pattern.length(); i++) {
            y = (int)(((long)y * x) % p);
        }

        for(int i = text.length() - pattern.length() - 1; i >= 0; i--) {
            h[i] = (int)(((long)x * h[i + 1] + (int)text.charAt(i) - (long)y * (int)text.charAt(i + pattern.length())) % p);
        }

        return h;
    }

    private static int polyHash(String s, int p, int x) {
        int hash = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            hash = (int)(((long)hash * x + (int)s.charAt(i)) % p);
        }

        return hash;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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

