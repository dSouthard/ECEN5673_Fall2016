import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    private double epsilon = 0.00000001;
    
    // Finds all line segments containing 4 points
    public FastCollinearPoints(Point[] in) {
        checkInput(in);

        // Track all the line segments
        segments = new ArrayList<>();

        // Start by sorting the input
        Point[] points = in.clone();
        Arrays.sort(points);

        // Examine 4 points at a time and check whether they lie on the same line segment
        for (int p = 0; p < points.length - 3; p++) {
            // For each, use P as the origin then sort the remaining points according to those slopes
            Point origin = points[p];
            Point[] sortedPoints = copyOver(points, origin);
            Comparator<Point> cmp = origin.slopeOrder();
            Arrays.sort(sortedPoints, cmp);

            // Go through sorted points and find if any 3 (or more) adjacent points have the same sloped.
            int i = 0;
            while (i < sortedPoints.length) {

                ArrayList<Point> group = new ArrayList<>(); 
                group.add(origin);
                group.add(sortedPoints[i]);

                double slope = origin.slopeTo(sortedPoints[i]);
                int j = i + 1;
                while (j < sortedPoints.length) {
                    double thisSlope = origin.slopeTo(sortedPoints[j]);
                    if (Math.abs(slope - thisSlope) < epsilon) {
                        group.add(sortedPoints[j]);
                        j++;
                    }
                    else {
                        // This slope does not match -- break out of loop
                        break;
                    }
                }

                // Broken out of slope comparison loop -- see how many points were with this slope
                if (group.size() > 3) {
                    // Only add when the origin in the minimum in the segment
                    group.sort(null);
                    if (group.get(0) == origin) {
                        segments.add(new LineSegment(group.get(0), group.get(group.size() - 1)));
                    }
                }

                // Move i forward to the next slope grouping
                i += group.size() - 1;
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

    // The number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    private Point[] copyOver(Point[] input, Point origin) {
        Point[] returnArray = new Point[input.length - 1];
        int p = 0, q = 0;
        while (p < input.length) {
            if (input[p] != origin) {
                returnArray[q] = input[p];
                p++;
                q++;
            } 
            else {
                p++;
            }
        }

        return returnArray;
    }

    // The line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        String filename = "input8.txt";
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

        FastCollinearPoints collinear2 = new FastCollinearPoints(points);
        StdOut.println("Number of line segments: " + collinear2.segments().length);
        for (LineSegment segment : collinear2.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdOut.println("Done drawing line segments.");


    }

}
