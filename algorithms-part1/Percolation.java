import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] site; // check blocked
	private int size; // length of one side
	private WeightedQuickUnionUF wqu;
	private WeightedQuickUnionUF wqu2;
	private int opened;

	private int getIdx(int row, int col) {
		return (row - 1) * size + col;
	}

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();

		size = n;
		opened = 0;
		site = new boolean[n * n + 1];
		wqu = new WeightedQuickUnionUF(n * n + 2);
		wqu2 = new WeightedQuickUnionUF(n * n + 1);
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size)
			throw new IllegalArgumentException();

		int idx = getIdx(row, col);
		if (!site[idx]) {
			site[idx] = true;
			if (idx <= size) {
				wqu.union(0, idx);
				wqu2.union(0, idx);
			}

			if (idx > size * (size - 1)) {
				wqu.union(idx, size * size + 1);
			}

			if (idx - size >= 1 && site[idx - size]) {
				wqu.union(idx, idx - size);
				wqu2.union(idx, idx - size);
			}
			if (idx + size <= size * size && site[idx + size]) {
				wqu.union(idx, idx + size);
				wqu2.union(idx, idx + size);
			}
			if (idx + 1 <= size * size && site[idx + 1]) {
				if (idx % size != 0) {
					wqu.union(idx, idx + 1);
					wqu2.union(idx, idx + 1);
				}
			}
			if (idx - 1 >= 1 && site[idx - 1]) {
				if (idx % size != 1) {
					wqu.union(idx, idx - 1);
					wqu2.union(idx, idx - 1);
				}
			}
			opened++;
		}

	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size)
			throw new IllegalArgumentException();

		int idx = getIdx(row, col);
		if (site[idx])
			return true;
		return false;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size)
			throw new IllegalArgumentException();

		int pos = (row - 1) * size + col;
		if (site[pos] && wqu2.find(pos) == wqu2.find(0))
			return true;
		return false;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return opened;
	}

	// does the system percolate?
	public boolean percolates() {
		if (wqu.find(size * size + 1) == wqu.find(0))
			return true;
		return false;
	}

	// test client (optional)
	public static void main(String[] args) {
	}
}