import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwtStr) {
        StringBuilder result = new StringBuilder();

        char[] lastColumn = bwtStr.toCharArray();
        char[] firstColumn = Arrays.copyOf(lastColumn, lastColumn.length);
        Arrays.sort(firstColumn);

        Map<Character, List<Integer>> positionsInFirstColumnTemp = new HashMap<>();
        for(int i = 0; i < firstColumn.length; i++) {
            if(!positionsInFirstColumnTemp.containsKey(firstColumn[i])) {
                positionsInFirstColumnTemp.put(firstColumn[i], new ArrayList<Integer>());
            }

            positionsInFirstColumnTemp.get(firstColumn[i]).add(i);
        }

        Map<Character, Iterator<Integer>> positionsInFirstColumn = new HashMap<>();
        for(Character key : positionsInFirstColumnTemp.keySet()) {
            positionsInFirstColumn.put(key, positionsInFirstColumnTemp.get(key).iterator());
        }

        int[] lastToFirst = new int[lastColumn.length];
        for(int i = 0; i < lastColumn.length; i++) {
            char symbol = lastColumn[i];
            lastToFirst[i] = positionsInFirstColumn.get(symbol).next();
        }

        int position = 0;
        result.append(firstColumn[0]);
        for(int i = 0; i < firstColumn.length - 1; i++) {
            result.append(lastColumn[position]);
            position = lastToFirst[position];
        }

        return result.reverse().toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();

        //long start = System.currentTimeMillis();
        System.out.println(inverseBWT(bwt));
        //System.out.println("Time: " + (System.currentTimeMillis() - start));
    }
}
