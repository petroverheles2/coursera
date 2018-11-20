import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PointSET {
    private final SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNotNull(p);
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNotNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);
        List<Point2D> points = new LinkedList<>();
        for (Point2D point : set) {
            if (point.x() >= rect.xmin() &&
                    point.x() <= rect.xmax() &&
                    point.y() >= rect.ymin() &&
                    point.y() <= rect.ymax() ) {
                points.add(point);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        if (set.isEmpty()) {
            return null;
        }

        Iterator<Point2D> iterator = set.iterator();

        Point2D nextPoint = iterator.next();
        double nextDist = p.distanceSquaredTo(nextPoint);

        Point2D minPoint = nextPoint;
        double minDist = nextDist;

        while (iterator.hasNext()) {
            nextPoint = iterator.next();
            nextDist = p.distanceSquaredTo(nextPoint);
            if (nextDist < minDist) {
                minPoint = nextPoint;
                minDist = nextDist;
            }
        }

        return minPoint;
    }

    private void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }
}
