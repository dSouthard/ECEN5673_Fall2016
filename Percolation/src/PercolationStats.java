import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] results;
    private double mean, stdDev;
    private double[] confidence;

    // Perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // Validate input
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Input arguments must be greater than 0.");
        this.trials = trials;
        mean = 0;
        stdDev = 0;
        confidence = new double[2];

        // Run T trials and record necessary number of open sites in order to percolate
        results = new double [trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // Open up a new site, check if it percolates
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            // Save the fraction of open sites in results
            results[i] = (double) perc.numberOfOpenSites()/(double) (n*n);
        }
        
        // Calculate results
        calculate();
    }

    private void calculate() {
        mean();
        stddev();
        confidenceLo();
        confidenceHi();
    }

    // Sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        stdDev = StdStats.stddev(results);
        return stdDev;
    }

    // Low end-point of 95% confidence interval
    public double confidenceLo() {
        confidence[0] = mean - (1.96*stdDev/Math.sqrt(trials));
        return confidence[0];
    }

    // High end-point of 95% confidence interval
    public double confidenceHi() {
        confidence[1] = mean + (1.96*stdDev/Math.sqrt(trials));
        return confidence[1];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mean: ").append(mean).append(System.getProperty("line.separator"));
        builder.append("Std Dev: ").append(stdDev).append(System.getProperty("line.separator"));
        builder.append("95% Confidence Interval: ").append(confidence[0]).append(" to ");
        builder.append(confidence[1]).append(System.getProperty("line.separator"));

        return builder.toString();
    }

    // Test Client
    // Takes two command-line arguments n and T
    public static void main(String[] args) {
        int n = 1, t = 1;
        if (args.length < 3) {
            StdOut.println("Input desired size of grid: ");
            n = StdIn.readInt();
            StdOut.println("Input desired number of trials: ");
            t = StdIn.readInt();
        }
        else {
            n = Integer.parseInt(args[1]);
            t = Integer.parseInt(args[2]);
        }

        // Perform T trials of n-by-n grids
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println(stats);
    }
}
