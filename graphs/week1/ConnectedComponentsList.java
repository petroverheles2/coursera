import java.util.*;

public class ConnectedComponentsList {
    private static boolean[] visited;

    private static List<Map<Integer, List<Integer>>> findConnectedComponents(List<List<Integer>> adj, List<List<Integer>> adjImg) {
        visited = new boolean[adjImg.size()];

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

    private static void explore(List<List<Integer>> adj, Integer v, Map<Integer, List<Integer>> component) {
        visited[v] = true;
        component.put(v, adj.get(v));
        for(Integer w : adj.get(v)) {
            if(!visited[w]) {
                explore(adj, w, component);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<List<Integer>>();
        List<List<Integer>> adjImg = new ArrayList<List<Integer>>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
            adjImg.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj.get(x - 1).add(y - 1);
            adjImg.get(x - 1).add(y - 1);
            adjImg.get(y - 1).add(x - 1);
        }
        System.out.println(findConnectedComponents(adj, adjImg));
    }
}

