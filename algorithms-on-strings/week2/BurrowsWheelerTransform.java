import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        StringBuilder result = new StringBuilder();

        List<String> rotations = new ArrayList<>(text.length());
        rotations.add(text);
        int startingIndex = text.length() - 1;
        for(int i = 0; i < text.length() - 1; i++) {
            int k = startingIndex;
            StringBuilder rotation = new StringBuilder();
            do {
                rotation.append(text.charAt(k));
                k++;
                if(k == text.length()) {
                    k = 0;
                }
            } while (k != startingIndex);
            rotations.add(rotation.toString());
            startingIndex--;
        }

        Collections.sort(rotations);
        for(String rotation : rotations) {
            result.append(rotation.charAt(text.length() - 1));
        }

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
