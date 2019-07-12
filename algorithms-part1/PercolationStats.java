import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] threshold;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		threshold = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			int count = 0;
			while (!perc.percolates()) {
				int chkOpen = perc.numberOfOpenSites();
				int tmpRow = StdRandom.uniform(n) + 1;
				int tmpCol = StdRandom.uniform(n) + 1;
				perc.open(tmpRow, tmpCol);
				if (chkOpen + 1 != perc.numberOfOpenSites())
					continue;
				count++;
			}
			threshold[i] = count / (double) (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(threshold);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
	}

	// test client (see below)
	public static void main(String[] args) {
		PercolationStats pstat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean                    = " + pstat.mean());
		System.out.println("stddev                  = " + pstat.stddev());
		System.out.println("95% confidence interval = [" + pstat.confidenceLo() + ", " + pstat.confidenceHi() + "]");
	}

}