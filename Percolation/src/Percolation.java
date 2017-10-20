import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites = 0;
    private int size;
    private boolean[] sites; // false == blocked, true == open
    private WeightedQuickUnionUF topUnions, bottomUnions;
    private int top = 0, bottom;

    // Creator method
    // Create n-by-b grid with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException(" Grid size must be greater than 0.");

        size = n;
        bottom = n * n + 1;

        // Array to keep status of each site, initialized to false
        sites = new boolean[size * size + 2];

        // Set the virtual top and bottom nodes to being open
        sites[top] = true;
        sites[bottom] = true;

        // Create UF object, account for virtual top/bottom nodes
        topUnions = new WeightedQuickUnionUF(n * n + 1);
        bottomUnions = new WeightedQuickUnionUF(n * n + 2);
    }

    private void validateInput(int row, int col) {
        if (row > size || col > size || row < 1 || col < 1) {
            throw new IndexOutOfBoundsException("Parameter out of grid bounds: row " + row + ", col: " + col);
        }
    }

    // Open site (row, col) if it's not already open
    public void open(int row, int col) {
        // Validate the inputs
        validateInput(row, col);

        // Check status of the site
        if (!isOpen(row, col)) {
            // Not blocked or already open, so now open it
            sites[getIndex(row, col)] = true; // Open
            openSites++;

            // Create unions with open neighbors
            connectNeighbors(row, col);
        }
    }

    // Check if this cell should be considered fully open based on its neighbors or its own location
    private void connectNeighbors(int row, int col) {
        // Top: connected to the top site --> open, by definition
        if (row == 1) {
            topUnions.union(top, getIndex(row, col));
            bottomUnions.union(top,  getIndex(row, col));
        }

        // Bottom: Connect to the bottom site only if not already percolating to prevent back wash
        if (row == size && !percolates()) {
            bottomUnions.union(bottom, getIndex(row, col));
        }

        // Top (not connected to top site)
        if (row > 1 && isOpen(row - 1, col)) {
            topUnions.union(getIndex(row, col), getIndex(row - 1, col));
            bottomUnions.union(getIndex(row, col), getIndex(row - 1, col));
        }

        // Bottom (not connected to bottom site)
        if (row < size && isOpen(row + 1, col)) {
            topUnions.union(getIndex(row, col), getIndex(row + 1, col));
            bottomUnions.union(getIndex(row, col), getIndex(row + 1, col));
            
        }

        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            topUnions.union(getIndex(row, col), getIndex(row, col - 1));
            bottomUnions.union(getIndex(row, col), getIndex(row, col - 1));
        }

        // Right
        if (col < size && isOpen(row, col + 1)) {
            topUnions.union(getIndex(row, col), getIndex(row, col + 1));
            bottomUnions.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    private int getIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        // Validate the inputs
        validateInput(row, col);
        return sites[getIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        // Validate the inputs
        validateInput(row, col);

        // Check if fully open
        return topUnions.connected(top, getIndex(row, col));
    }

    public boolean percolates() {
        // Check if this instance percolates
        return bottomUnions.connected(top, bottom);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    public static void main(String [] args) {
        StdOut.println("Enter in desired grid size: ");
        int size = StdIn.readInt();

        Percolation perc = new Percolation(size);
        while (!perc.percolates()) {
            StdOut.println("Enter in desired row/col to open: ");
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            if (perc.isOpen(row, col)) {
                StdOut.println("This site was already opened.");
                continue;
            }
            perc.open(row, col);

            if (!perc.isOpen(row, col)) {
                StdOut.println("Site failed to open!");
            }
            if (perc.isFull(row, col))
                StdOut.println("This site was fully opened!");
            StdOut.println("There are now " + perc.openSites + " open sites.");
        }
        StdOut.println("Finished with " + String.valueOf(perc.numberOfOpenSites()) + " sites open.");
        // Check that everything is as it should be
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++)
                if (perc.isFull(i, j)) {
                    StdOut.println("Site " + i + ", " + j + " is fully open.");
                }
        double percentage = (double) perc.numberOfOpenSites() * 100.0/(double) (size * size);
        System.out.println("Resulting value: " + String.valueOf(percentage) + " percent of sites open.");
    }
}
