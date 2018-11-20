import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {
    private static boolean[] visited;

    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        ArrayList<Integer> order = new ArrayList<Integer>();

        dfs(adj, order);

        Collections.reverse(order);

        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, ArrayList<Integer> order) {
        visited = new boolean[adj.length];

        for(int i = 0; i < adj.length; i++) {
            if(!visited[i]) {
                explore(adj, i, order);
            }
        }
    }

    private static void explore(ArrayList<Integer>[] adj, Integer v, ArrayList<Integer> order) {
        visited[v] = true;
        for(Integer w : adj[v]) {
            if(!visited[w]) {
                explore(adj, w, order);
            }
        }
        order.add(v);
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
        ArrayList<Integer> order = toposort(adj);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}

