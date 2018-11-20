import java.util.*;

public class Dijkstra {
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        final int[] dist = new int[adj.length];
        for(int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[s] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer u, Integer v) {
                return Integer.valueOf(dist[u]).compareTo(dist[v]);
            }
        });

        for(int i = 0; i < dist.length; i++) {
            queue.add(i);
        }

        while(!queue.isEmpty()) {
            int u = queue.poll();
            int adjIndex = 0;
            for(Integer v : adj[u]) {
                if(dist[u] == Integer.MAX_VALUE) {
                    continue;
                }

                if(dist[v] == Integer.MAX_VALUE || dist[v] > dist[u] + cost[u].get(adjIndex)) {
                    dist[v] = dist[u] + cost[u].get(adjIndex);
                    queue.remove(v);
                    queue.add(v);
                }
                adjIndex++;
            }
        }

        return dist[t] == Integer.MAX_VALUE ? -1 : dist[t];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}

