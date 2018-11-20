import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        segments = new ArrayList<>();

        if (points.length < 4) {
            return;
        }

        Point[] aux = new Point[4];
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points, i, points.length, points[i].slopeOrder());

            Point origin = points[i];

            for (int k = i + 1; k < points.length - 2; k++) {
                if (origin.slopeOrder().compare(points[k], points[k + 1]) == 0
                        && origin.slopeOrder().compare(points[k + 1], points[k + 2]) == 0) {
                    aux[0] = origin;
                    aux[1] = points[k];
                    aux[2] = points[k + 1];
                    aux[3] = points[k + 2];
                    Arrays.sort(aux);
                    LineSegment segment = new LineSegment(aux[0], aux[3]);
                    segments.add(segment);
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
