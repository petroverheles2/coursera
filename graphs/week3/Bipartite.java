import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {

    private enum PartCodes {
        UNDISCOVERED, WHITE, BLACK
    }

    private static int bipartite(ArrayList<Integer>[] adj, int verticesNumber) {
        PartCodes[] partCodes = new PartCodes[verticesNumber];
        for(int i = 0; i < verticesNumber; i++) {
            partCodes[i] = PartCodes.UNDISCOVERED;
        }

        for(int x = 0; x < verticesNumber; x++) {
            if(partCodes[x] == PartCodes.UNDISCOVERED) {
                partCodes[x] = PartCodes.WHITE;
            } else {
                continue;
            }

            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(x);
            while (!queue.isEmpty()) {
                int u = queue.poll();
                PartCodes uPartCode = partCodes[u];
                PartCodes adjacentPartCode = PartCodes.BLACK;
                if(uPartCode == PartCodes.BLACK) {
                    adjacentPartCode = PartCodes.WHITE;
                }
                for(Integer v : adj[u]) {
                    if(partCodes[v] == PartCodes.UNDISCOVERED) {
                        queue.add(v);
                        partCodes[v] = adjacentPartCode;
                    } else if(partCodes[v] != adjacentPartCode) {
                        return 0;
                    }
                }
            }
        }

        return 1;

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
        System.out.println(bipartite(adj, n));
    }
}

