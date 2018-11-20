import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int n = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNotNull(p);

        if (n == 0) {
            Node node = new Node();
            node.p = p;
            node.rect = new RectHV(0, 0, 1, 1);
            root = node;
            n = 1;
        } else if (insert(root, p, true)) {
            n++;
        }

    }

    private boolean insert(Node parent, Point2D p, boolean compareX) {
        if (parent.p.equals(p)) {
            return false;
        }

        boolean goLeft;
        if (compareX) {
            goLeft = p.x() < parent.p.x();
        } else {
            goLeft = p.y() < parent.p.y();
        }

        if (goLeft) {
            if (parent.lb != null) {
                return insert(parent.lb, p, !compareX);
            } else {
                parent.lb = createNode(parent, p, compareX, true);
                return true;
            }
        } else {
            if (parent.rt != null) {
                return insert(parent.rt, p, !compareX);
            } else {
                parent.rt = createNode(parent, p, compareX, false);
                return true;
            }
        }
    }

    private Node createNode(Node parent, Point2D p, boolean compareX, boolean goLeft) {
        Node node = new Node();
        node.p = p;
        if (compareX) {
            if (goLeft) {
                node.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
            } else {
                node.rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
            }
        } else {
            if (goLeft) {
                node.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
            } else {
                node.rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNotNull(p);

        if (root == null) {
            return false;
        }

        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean compareX) {
        if (node.p.equals(p)) {
            return true;
        }

        boolean goLeft;
        if (compareX) {
            goLeft = p.x() < node.p.x();
        } else {
            goLeft = p.y() < node.p.y();
        }

        if (goLeft) {
            if (node.lb != null) {
                return contains(node.lb, p, !compareX);
            }
        } else {
            if (node.rt != null) {
                return contains(node.rt, p, !compareX);
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node == null) {
            return;
        }

        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            StdDraw.setPenColor();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            StdDraw.setPenColor();
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.012);
        node.p.draw();
        StdDraw.setPenRadius();

        draw(node.lb, !vertical);
        draw(node.rt, !vertical);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);

        List<Point2D> points = new ArrayList<>();

        range(root, rect, points);

        return points;
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {
        if (node == null) {
            return;
        }

        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                points.add(node.p);
            }

            range(node.lb, rect, points);
            range(node.rt, rect, points);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);

        return nearest(this.root, p, new NearestEntry(null, Double.POSITIVE_INFINITY)).point;
    }

    private NearestEntry nearest(Node node, Point2D p, NearestEntry nearestEntry) {
        if (node == null) {
            return nearestEntry;
        }

        double minimumDistanceSquareCandidate = node.p.distanceSquaredTo(p);
        if (nearestEntry.distanceSquare > minimumDistanceSquareCandidate) {
            nearestEntry = new NearestEntry(node.p, minimumDistanceSquareCandidate);
        }

        if (node.lb != null && nearestEntry.distanceSquare > node.lb.rect.distanceSquaredTo(p)) {
            nearestEntry = nearest(node.lb, p, nearestEntry);
        }

        if (node.rt != null && nearestEntry.distanceSquare > node.rt.rect.distanceSquaredTo(p)) {
            nearestEntry = nearest(node.rt, p, nearestEntry);
        }

        return nearestEntry;

    }

    private void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    private static class NearestEntry {
        Point2D point;
        double distanceSquare;

        NearestEntry(Point2D point, double distanceSquare) {
            this.point = point;
            this.distanceSquare = distanceSquare;
        }
    }

    public static void main(String[] args) {
//        KdTree kdTree = new KdTree();
//        kdTree.insert(new Point2D(0.5, 0.5));
//        kdTree.insert(new Point2D(0.1, 0.6));
//        kdTree.insert(new Point2D(0.6, 0.1));
//        kdTree.insert(new Point2D(.3, .3));
//        kdTree.insert(new Point2D(0.7, 0));
//        kdTree.insert(new Point2D(0.1, 0.2));
//
//        kdTree.draw();
//        System.out.println(kdTree.contains(new Point2D(0.1, 0.5)));
    }
}
