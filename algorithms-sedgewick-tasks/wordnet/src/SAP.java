import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph digraph;

    public SAP(Digraph digraph) {
        if (digraph == null) {
            throw new IllegalArgumentException();
        }

        this.digraph = new Digraph(digraph);
    }

    /** length of shortest ancestral path between v and w;
     *  -1 if no such path.
     *
     * @param v
     * @param w
     * @return
     */
    public int length(int v, int w) {
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(digraph, w);
        return length(pathsV, pathsW);
    }

    private int length(BreadthFirstDirectedPaths pathsV, BreadthFirstDirectedPaths pathsW) {
        int numberOfVertices = digraph.V();
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < numberOfVertices; i++) {
            if (pathsV.hasPathTo(i) && pathsW.hasPathTo(i)) {
                minLength = Math.min(minLength,
                        pathsV.distTo(i) + pathsW.distTo(i));
            }
        }
        if (minLength == Integer.MAX_VALUE) return -1;
        return minLength;
    }

    /** a common ancestor of v and w that participates in a shortest a
     *  ncestral path; -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(digraph, w);
        return anscestor(pathsV, pathsW);
    }

    private int anscestor(BreadthFirstDirectedPaths pathsV, BreadthFirstDirectedPaths pathsW) {
        int numberOfVertices = digraph.V();
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < numberOfVertices; i++) {
            if (pathsV.hasPathTo(i) && pathsW.hasPathTo(i)) {
                int cur = pathsV.distTo(i) + pathsW.distTo(i);
                if (cur < minLength) {
                    minLength = cur;
                    result = i;
                }
            }
        }
        if (minLength == Integer.MAX_VALUE) return -1;
        return result;
    }

    /** length of shortest ancestral path between any numberOfVertices in v and
     *  any numberOfVertices in w; -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(digraph, w);
        return length(pathsV, pathsW);
    }

    /** a common ancestor that participates in shortest ancestral path;
     *  -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(digraph, w);
        return anscestor(pathsV, pathsW);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        StdOut.println("Initialize successfully...");
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            if (v < 0 || w < 0) break;
            long begin = System.currentTimeMillis();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.println("Total time used is: "
                    + (System.currentTimeMillis() - begin));
        } // end while loop
        return;
    }

}