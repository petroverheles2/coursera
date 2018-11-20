import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean OPEN = true;
    private static final boolean CLOSED = false;

    private final boolean[] sites;
    private final int dimension;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF unionUF;
    private final int top;
    private final int bottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        dimension = n;
        int size = n*n;
        sites = new boolean[size];
        for (int i = 0; i < sites.length; i++) {
            sites[i] = CLOSED;
        }
        unionUF = new WeightedQuickUnionUF(size + 2);
        top = size;
        bottom = size + 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int siteIndex = index(row, col);

        sites[siteIndex] = OPEN;
        numberOfOpenSites++;

        // connect with open neighbours
        if (row > 1) { // top neighbour
            if (isOpen(row - 1, col)) {
                unionUF.union(siteIndex, index(row - 1, col));
            }
        }

        if (row < dimension) { // bottom neighour
            if (isOpen(row + 1, col)) {
                unionUF.union(siteIndex, index(row + 1, col));
            }
        }

        if (col > 1) { // left neighbour
            if (isOpen(row, col - 1)) {
                unionUF.union(siteIndex, index(row, col - 1));
            }
        }

        if (col < dimension) { // right neighbour
            if (isOpen(row, col + 1)) {
                unionUF.union(siteIndex, index(row, col + 1));
            }
        }

        // connect to top general or bottom general element
        if (row == 1) {
            unionUF.union(siteIndex, top);
        }

        if (row == dimension) {
            unionUF.union(siteIndex, bottom);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[index(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return unionUF.connected(index(row, col), top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionUF.connected(top, bottom);
    }

    private int index(int row, int col) {
        validateRange(row, col);
        return (row - 1) * dimension + (col - 1);
    }

    private void validateRange(int row, int col) {
        if (row < 1
                || row > dimension
                || col < 1
                || col > dimension) {
            throw new IllegalArgumentException();
        }
    }

    private void draw() {
        System.out.println();
        System.out.println(String.format("Size = %sx%s", dimension, dimension));
        for (int row = 1; row <= dimension; row++) {
            for (int col = 1; col <= dimension; col++) {
                System.out.print(isOpen(row, col) ? "X" : "0");
            }
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(7);
        percolation.open(1, 2);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.open(5, 3);
        percolation.draw();
        System.out.println("percolation.percolates() " + percolation.percolates());
        System.out.println("percolation.isFull(4, 3) " + percolation.isFull(4, 3));
        System.out.println("percolation.isFull(6, 3) " + percolation.isFull(6, 3));
        System.out.println("percolation.numberOfOpenSites() " + percolation.numberOfOpenSites());
    }
}
