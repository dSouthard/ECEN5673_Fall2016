import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private int x, y;

    // Constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // Draws the line segment from this point to that point
    public void drawTo(Point that) {
        if (that == null) throw new NullPointerException("Comparing to a null point");
        StdDraw.line(x, y, that.x, that.y);
    }

    // String representation
    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }

    // Compare two points by y-coordinates, breaking ties by x-coordinate
    @Override
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException("Comparing to a null point");
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else if (this.y > that.y || (this.y == that.y && this.x > that.x)) return 1;
        return 0;
    }


    // The slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("Comparing to a null point");
        // Check if that point is the same as this point or if it's a vertical line
        else if (x == that.x) {
            if (y == that.y) {
                // Same point
                return Double.NEGATIVE_INFINITY;
            }
            else {
                // Vertical line
                return Double.POSITIVE_INFINITY;
            }
        }
        else if (y == that.y) {
            // Horizontal line
            return +0.0;
        }
        return ((double) this.y - (double) that.y)/ ((double) this.x - (double) that.x);
    }

    // Compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1 == null || o2 == null) throw new NullPointerException("Comparing null point[s]");
                double slope1 = slopeTo(o1);
                double slope2 = slopeTo(o2);
                if (slope1 < slope2) return -1;
                if (slope2 < slope1) return 1;
                return 0;
            }
        };

        return comparator;
    }

}
