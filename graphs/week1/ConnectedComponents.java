import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {
    private static boolean[] visited;
    private static int result = 0;

    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        visited = new boolean[adj.length];

        for(int i = 0; i < adj.length; i++) {
            if(!visited[i]) {
                explore(adj, i);
                result++;
            }
        }

        return result;
    }


    private static void explore(ArrayList<Integer>[] adj, Integer v) {
        visited[v] = true;
        for(Integer w : adj[v]) {
            if(!visited[w]) {
                explore(adj, w);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(numberOfComponents(adj));
    }
}

