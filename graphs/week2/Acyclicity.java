import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
    private static boolean[] visited;

    private static int acyclic(ArrayList<Integer>[] adj) {
        for(int v = 0; v < adj.length; v++) {
            for(Integer u : adj[v]) {
                if(reach(adj, u, v) == 1 && reach(adj, v, u) == 1) {
                    return 1;
                }
            }
        }

        return 0;
    }

    private static void explore(ArrayList<Integer>[] adj, Integer v) {
        visited[v] = true;
        for(Integer w : adj[v]) {
            if(!visited[w]) {
                explore(adj, w);
            }
        }
    }

    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        visited = new boolean[adj.length];

        explore(adj, x);

        return visited[y] ? 1 : 0;
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
        }

        System.out.println(acyclic(adj));
    }
}

