public class LineSegment {
    private Point p, q;
    // Constructs the line segment between points p and q
    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }
    
    // Draws this line segment
    public void draw() {
        p.drawTo(q);
    }
    
    // String representation
    @Override
    public String toString() {
        return "Point 1: " + p.toString() + "; Point 2: " + q.toString();
    }
}
