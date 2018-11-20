import java.util.*;

public class ConnectingPoints {
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;

        Point[] points = new Point[x.length];
        for(int i = 0; i < x.length; i++) {
            points[i] = new Point(x[i], y[i], i);
        }

        DisjointDataset<Point> disjointDataset = new DisjointDataset<Point>();

        Set<Edge> roads = new HashSet<Edge>();

        List<Edge> edges = new ArrayList<Edge>();
        for(int i = 0; i < points.length - 1; i++) {
            for(int k = i + 1; k < points.length; k++) {
                Edge edge = new Edge();
                edge.point1 = points[i];
                edge.point2 = points[k];
                edge.weight = Math.sqrt(Math.pow(points[i].x - points[k].x, 2) + Math.pow(points[i].y - points[k].y, 2));
                edges.add(edge);
            }
        }

        for(int i = 0; i < points.length; i++) {
            disjointDataset.make(points[i]);
        }

        Collections.sort(edges);

        for(Edge edge : edges) {
            if(!disjointDataset.find(edge.point1).equals(disjointDataset.find(edge.point2))) {
                roads.add(edge);
                disjointDataset.union(edge.point1, edge.point2);
            }
        }

        for(Edge road : roads) {
            result += road.weight;
        }

        //write your code here
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }

    private static class DisjointDataset<T extends Comparable<T>> {
        Map<T, TreeSet<T>> map = new HashMap<T, TreeSet<T>>();

        public T find(T o) {
            return map.get(o).first();
        }

        public void union(T o1, T o2) {
            if(find(o1).compareTo(find(o2)) < 0) {
                TreeSet<T> t = map.get(o1);
                t.addAll(map.get(o2));
                for(T o : map.get(o2)) {
                    map.put(o, t);
                }
            } else {
                TreeSet<T> t = map.get(o2);
                t.addAll(map.get(o1));
                for(T o : map.get(o1)) {
                    map.put(o, t);
                }            }
        }

        public void make(T o) {
            TreeSet<T> treeSet = new TreeSet<T>();
            treeSet.add(o);
            map.put(o, treeSet);
        }
    }

    private static class Point implements Comparable<Point> {
        int x;
        int y;
        int index;

        Point(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            return index == point.index;

        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public int compareTo(Point o) {
            return Integer.compare(index, o.index);
        }
    }

    private static class Edge implements Comparable {
        double weight;
        Point point1;
        Point point2;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (!point1.equals(edge.point1)) return false;
            return point2.equals(edge.point2);

        }

        @Override
        public int hashCode() {
            int result = point1.hashCode();
            result = 31 * result + point2.hashCode();
            return result;
        }

        @Override
        public int compareTo(Object o) {
            Edge edge = (Edge) o;
            return Double.compare(weight, edge.weight);
        }
    }
}

