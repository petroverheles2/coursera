
import java.util.Arrays;
import java.util.Random;

public class MatrixSP {
    private final int topColumn;

    private final double[][] energies;
    private final double[][] distTo;
    private final int pathTo[][];
    private final int rowNum;
    private final int colNum;
    private final boolean[][] processed;

    public MatrixSP(double[][] energies, int columnInTopRow) {
        this.topColumn = columnInTopRow;


        rowNum = energies.length;
        colNum = energies[0].length;

        this.energies = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            System.arraycopy(energies[i], 0, this.energies[i], 0, colNum);
        }

        pathTo = new int[rowNum][colNum];
        distTo = new double[rowNum][colNum];
        processed = new boolean[rowNum][colNum];

        for (int col = 0; col < colNum; col++) {
            for (int row = 0; row < rowNum; row++) {
                pathTo[row][col] = -1;
                distTo[row][col] = Double.POSITIVE_INFINITY;
                processed[row][col] = false;
            }
        }

        distTo[0][columnInTopRow] = 0; // distance to itself

        if (rowNum == 1) {
            return;
        }

        calculatePoint(columnInTopRow, 0);
    }

    private void calculatePoint(int column, int row) {

        int nextRow = row + 1;

        if (nextRow > rowNum - 1) {
            return;
        }

        if (processed[row][column]) {
            return;
        }

        relax(column, row, column, nextRow);

        if (column > 0) {
            int leftColumn = column - 1;
            relax(column, row, leftColumn, nextRow);
        }

        if (column < colNum - 1) {
            int rightColumn = column + 1;
            relax(column, row, rightColumn, nextRow);
        }

        processed[row][column] = true;

        calculatePoint(column, nextRow);

        if (column > 0) {
            int leftColumn = column - 1;
            calculatePoint(leftColumn, nextRow);
        }

        if (column < colNum - 1) {
            int rightColumn = column + 1;
            calculatePoint(rightColumn, nextRow);
        }

    }

    private void relax(int fromColumn, int fromRow, int toColumn, int toRow) {
        if (pathTo[toRow][toColumn] == -1) {
            pathTo[toRow][toColumn] = fromColumn;
            distTo[toRow][toColumn] = distTo[fromRow][fromColumn] + energies[toRow][toColumn];
        } else if (distTo[toRow][toColumn] > distTo[fromRow][fromColumn] + energies[toRow][toColumn]) {
            distTo[toRow][toColumn] = distTo[fromRow][fromColumn] + energies[toRow][toColumn];
            pathTo[toRow][toColumn] = fromColumn;
        }
    }

    public int[] pathToBottom(int column) {

        if (rowNum == 1) {
            if(topColumn == column) {
                return new int[]{column};
            } else {
                return new int[]{-1};
            }
        }

        int[] path = new int[rowNum];
        int col = column;

        for(int row = rowNum - 1; row > 0; row--) {
            path[row] = col;
            col = pathTo[row][col];
        }

        path[0] = topColumn;

        return path;
    }

    public double distanceToBottom(int column) {
        return distTo[rowNum - 1][column];
    }

    private void printEnergies() {
        System.out.println();
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                System.out.print( String.format("%4.0f", energies[row][col]) + "  ");
            }

            System.out.println();
        }
    }

    private void printPathTo() {
        System.out.println();
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                System.out.print(pathTo[row][col] + "  ");
            }

            System.out.println();
        }
    }

    private void printDistTo() {
        System.out.println();
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                System.out.print(distTo[row][col] + "  ");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        int colNum = 300;
        int rowNum = 600;
        double[][] energies = new double[rowNum][colNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (col == 0 || row == 0 || col == colNum - 1 || row == rowNum - 1) {
                    energies[row][col] = 1000;
                } else {
                    energies[row][col] = rnd.nextInt(9) + 1;
                }
            }
        }
        MatrixSP matrixSP = new MatrixSP(energies, 2);
        matrixSP.printEnergies();
        matrixSP.printPathTo();
        matrixSP.printDistTo();
        int bottomColumn = 2;
        System.out.println(String.format("distance from %d to %d: ", matrixSP.topColumn, bottomColumn) + matrixSP.distanceToBottom(bottomColumn));
        System.out.println(String.format("path from %d to %d: ", matrixSP.topColumn, bottomColumn) + Arrays.toString(matrixSP.pathToBottom(bottomColumn)));
    }
}
