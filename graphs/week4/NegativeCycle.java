import java.util.*;

public class NegativeCycle {
    public static int INFINITY = 1001;

    private static int negativeCycle(List<List<Integer>> adj, List<List<Integer>> adjImg, List<Map<Integer, Integer>> cost) {
        List<Map<Integer, List<Integer>>> connectedComponents = new ConnectedComponentsList().findConnectedComponents(adj, adjImg);

        for(Map<Integer, List<Integer>> connectedComponent : connectedComponents) {
            int result = negativeCycleInConnectedGraph(connectedComponent, cost);
            if(result == 1) {
                return result;
            }
        }

        return 0;
    }

    private static int negativeCycleInConnectedGraph(Map<Integer, List<Integer>> connectedComponent, List<Map<Integer, Integer>> cost) {
        final Map<Integer, Integer> dist = new HashMap<Integer, Integer>();

        for(Integer u : connectedComponent.keySet()) {
            dist.put(u, INFINITY);
        }

        dist.put(connectedComponent.keySet().iterator().next(), 0);

        for (int i = 0; i < dist.size() - 1; i++) {
            for(Integer u : connectedComponent.keySet()) {
                for(Integer v : connectedComponent.get(u)) {
                    int dist_v = dist.get(v);
                    int dist_u = dist.get(u);
                    int cost_u_v = cost.get(u).get(v);

                    if (dist_v > dist_u + cost_u_v) {
                        dist.put(v, dist_u + cost_u_v);
                    }
                }
            }
        }

        //additional iteration
        for(Integer u : connectedComponent.keySet()) {
            for(Integer v : connectedComponent.get(u)) {
                if (dist.get(v) > dist.get(u) + cost.get(u).get(v)) {
//                    System.out.println(u);
//                    System.out.println(v);
//                    System.out.println(dist.get(v));
                    return 1;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<List<Integer>>();
        List<List<Integer>> adjImg = new ArrayList<List<Integer>>();
        List<Map<Integer, Integer>> cost = new ArrayList<Map<Integer, Integer>>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
            adjImg.add(new ArrayList<Integer>());
            cost.add(new HashMap<Integer, Integer>());
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj.get(x - 1).add(y - 1);
            adjImg.get(x - 1).add(y - 1);
            adjImg.get(y - 1).add(x - 1);
            cost.get(x - 1).put(y - 1, w);
        }

        System.out.println(negativeCycle(adj, adjImg, cost));
    }


    private static class ConnectedComponentsList {
        private boolean[] visited;

        public List<Map<Integer, List<Integer>>> findConnectedComponents(List<List<Integer>> adj, List<List<Integer>> adjImg) {
            visited = new boolean[adj.size()];

            List<Map<Integer, List<Integer>>> connectedComponents = new ArrayList<Map<Integer, List<Integer>>>();

            for(int i = 0; i < adjImg.size(); i++) {
                if(!visited[i]) {
                    Map<Integer, List<Integer>> component = new HashMap<Integer, List<Integer>>();
                    connectedComponents.add(component);
                    explore(adjImg, i, component);
                }
            }

            for(Map<Integer, List<Integer>> c : connectedComponents) {
                for(Integer u : c.keySet()) {
                    c.get(u).retainAll(adj.get(u));
                }
            }

            return connectedComponents;
        }

        private void explore(List<List<Integer>> adj, Integer v, Map<Integer, List<Integer>> component) {
            visited[v] = true;
            component.put(v, adj.get(v));
            for(Integer w : adj.get(v)) {
                if(!visited[w]) {
                    explore(adj, w, component);
                }
            }
        }
    }
}

