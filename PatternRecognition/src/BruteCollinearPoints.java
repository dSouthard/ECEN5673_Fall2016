import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;
    private double epsilon = 0.00000001;

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] in) {
        checkInput(in);
        Point[] points = in.clone();
        Arrays.sort(points);
        segments = new ArrayList<>();
        // Examine 4 points at a time and check whether they lie on the same line segment
        for (int p = 0; p < points.length; p++) {
            Point[] group = new Point[4];
            group[0] = points[p];
            for (int q = p + 1; q < points.length; q++) {
                group[1] = points[q];
                for (int r = q + 1; r < points.length; r++) {
                    group[2] = points[r];
                    for (int s = r + 1; s < points.length; s++) {
                        group[3] = points[s];
                        if (checkCollinear(points[p], points[q], points[r], points[s])) {
                            // Add this line segment
                            Arrays.sort(group);
                            segments.add(new LineSegment(group[0], group[3]));
                        }
                    }
                }
            }
        }
    }
    
    private void checkInput(Point[] points) {
        if (points == null) throw new NullPointerException("Cannot accept null input");
//        if (points.length < 4) throw new IllegalArgumentException("Require at least 4 points.");
        Arrays.sort(points);
        Point last = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i] == null)  throw new NullPointerException("Illegal input: null");
            if (points[i] == last) throw new IllegalArgumentException("Illegal input: duplicates.");
            last = points[i];
        }
    }

    // check if all slopes are equal
    private boolean checkCollinear(Point p, Point q, Point r, Point s) {
        double slopePq = p.slopeTo(q);
        double slopePr = p.slopeTo(r);
        double slopePs = p.slopeTo(s);
        return (Math.abs(slopePq - slopePr) < epsilon) && (Math.abs(slopePq - slopePs) < epsilon);
    }

    // The number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // The line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        String filename = "input100.txt";
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        StdOut.println("Read in all lines.");

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        StdOut.println("Drew all points.");

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        
        StdOut.println("Done.");

    } 
}
