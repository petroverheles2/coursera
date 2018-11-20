import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class StronglyConnected {
    private static boolean[] visited;
    private static int result = 0;

    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {

        ArrayList<Integer>[] adjR = reverseGraph(adj);

        List<Integer> reversedPostOrder = toposort(adjR);

        visited = new boolean[adjR.length];

        for(Integer v : reversedPostOrder) {
            if(!visited[v]) {
                explore(adj, v);
                result++;
            }
        }

        return result;
    }

    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] adjR = (ArrayList<Integer>[])new ArrayList[adj.length];
        for(int w = 0; w < adj.length; w++) {
            adjR[w] = new ArrayList<Integer>();
        }

        for(int v = 0; v < adj.length; v++) {
            for(Integer w : adj[v]) {
                adjR[w].add(v);
            }
        }

        return adjR;
    }

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
                exploreForOrder(adj, i, order);
            }
        }
    }

    private static void exploreForOrder(ArrayList<Integer>[] adj, Integer v, ArrayList<Integer> order) {
        visited[v] = true;
        for(Integer w : adj[v]) {
            if(!visited[w]) {
                exploreForOrder(adj, w, order);
            }
        }
        order.add(v);
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
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}

