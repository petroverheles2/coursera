import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    final private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        segments = new ArrayList<>();

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        if (points.length < 4) {
            return;
        }

        //Merge.sort(points);

        Point[] aux = new Point[4];

        for (int i = 0; i < points.length - 3; i++) {
            for (int k = i + 1; k < points.length - 2; k++) {
                for (int m = k + 1; m < points.length - 1; m++) {
                    for (int n = m + 1; n < points.length; n++) {
                        Point p1 = points[i];
                        Point p2 = points[k];
                        Point p3 = points[m];
                        Point p4 = points[n];

                        if (p1.slopeOrder().compare(p2, p3) == 0 && p2.slopeOrder().compare(p3, p4) == 0) {
                            aux[0] = p1;
                            aux[1] = p2;
                            aux[2] = p3;
                            aux[3] = p4;
                            Arrays.sort(aux);
                            LineSegment segment = new LineSegment(aux[0], aux[3]);
                            segments.add(segment);
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
