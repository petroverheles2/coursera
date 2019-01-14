public class MatrixSP {
    private double[][] energies;

    private double[][] distTo;
    private int pathTo[][];
    private final int rowNum;
    private final int colNum;

    public MatrixSP(double[][] energies, int topColumn) {
        this.energies = energies;

        colNum = energies.length;
        rowNum = energies[0].length;

        pathTo = new int[colNum][rowNum];
        for (int col = 0; col < pathTo.length; col++) {
            for (int row = 0; row < pathTo[0].length; row++) {
                pathTo[col][row] = -1;
            }
        }


        distTo = new double[colNum][rowNum];
        for (int col = 0; col < distTo.length; col++) {
            for (int row = 0; row < distTo[0].length; row++) {
                distTo[col][row] = Double.POSITIVE_INFINITY;
            }
        }

        distTo[topColumn][0] = 0; // distance to itself








        int currentRow = 1;

        if (currentRow > rowNum - 1) {
            return;
        }

        distTo[topColumn][currentRow] = energies[topColumn][currentRow];
        pathTo[topColumn][currentRow] = topColumn;

        if (topColumn != 0) {
            int leftBottomNeighbour = topColumn - 1;
            distTo[leftBottomNeighbour][currentRow] = energies[leftBottomNeighbour][currentRow];
            pathTo[leftBottomNeighbour][currentRow] = topColumn;
        }

        if (topColumn != colNum - 1) {
            int rightBottomNeighbour = topColumn + 1;
            distTo[rightBottomNeighbour][currentRow] = energies[rightBottomNeighbour][currentRow];
            pathTo[rightBottomNeighbour][currentRow] = topColumn;
        }







        int nextRow = 2;
        if (nextRow > rowNum - 1) {
            return;
        }



    }

    public int[] pathToBottom(int column) {
        int col = column;

        if (pathTo[col][rowNum - 1] == -1) {
            return null;
        }

        int[] path = new int[rowNum];
        for (int row = rowNum - 1; row > 0; row--) {
            col = pathTo[col][row];
            path[row] = col;
        }

        return path;
    }

    public double distanceToBottom(int column) {
        return distTo[column][rowNum - 1];
    }
}
