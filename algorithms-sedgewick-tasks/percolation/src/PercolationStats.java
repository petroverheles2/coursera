import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by petroverheles on 1/31/18.
 */
public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = experiment(n);
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        double fr = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - fr;
        confidenceHi = mean + fr;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    private double experiment(int n) {
        Percolation percolation = new Percolation(n);
        int size = n*n;
        int[] indexes = new int[size];
        for (int i = 0; i < size; i++) {
            indexes[i] = i;
        }

        int trial = 0;
        while (!percolation.percolates()) {
            int randomIndex = StdRandom.uniform(size - trial);
            int index = indexes[randomIndex];
            int row = index / n + 1;
            int col = index % n + 1;
            percolation.open(row, col);
            int last = size - trial - 1;
            int tmpIntex = indexes[last];
            indexes[last] = index;
            indexes[randomIndex] = tmpIntex;
            trial++;
        }

        return trial / (double) size;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                     = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval  = [" + stats.confidenceLo() + " " +  stats.confidenceHi() + "]");
    }
}
