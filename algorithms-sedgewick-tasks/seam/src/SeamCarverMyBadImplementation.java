import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarverMyBadImplementation {

    private Picture picture;
    private double[][] energies;

    // create a seam carver object based on the given picture
    public SeamCarverMyBadImplementation(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
        this.energies = new double[height()][width()];

        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energies[row][col] = energy(col, row);
            }
        }
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() || y < 0 || y > height()) {
            throw new IllegalArgumentException();
        }

        if (x == 0 ||
            x == width() - 1 ||
            y == 0 ||
            y == height() - 1) {
            return 1000;
        }

        Color colorOfXminusOne = picture.get(x - 1, y);
        Color colorOfXPlusOne = picture.get(x + 1, y);
        int rx = colorOfXminusOne.getRed() - colorOfXPlusOne.getRed();
        int gx = colorOfXminusOne.getGreen() - colorOfXPlusOne.getGreen();
        int bx = colorOfXminusOne.getBlue() - colorOfXPlusOne.getBlue();

        Color colorOfYMinusOne = picture.get(x, y - 1);
        Color colorOfYPlusOne = picture.get(x, y + 1);
        int ry = colorOfYMinusOne.getRed() - colorOfYPlusOne.getRed();
        int gy = colorOfYMinusOne.getGreen() - colorOfYPlusOne.getGreen();
        int by = colorOfYMinusOne.getBlue() - colorOfYPlusOne.getBlue();

        int deltaSqrX = rx * rx + gx * gx + bx * bx;
        int deltaSqrY = ry * ry + gy * gy + by * by;

        return Math.sqrt(deltaSqrX + deltaSqrY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] preservedEnergies = energies;
        energies = createRotatedEnergies();

        int[] horizontalSeam = findVerticalSeam();

        energies = preservedEnergies;

        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] verticalSeam = null;

        double shortestDist = Double.POSITIVE_INFINITY;

        for (int topCol = 0; topCol < energies[0].length; topCol++) {
            MatrixSP matrixSP = new MatrixSP(energies, topCol);
            for (int botCol = 0; botCol < energies[0].length; botCol++) {
                if (shortestDist > matrixSP.distanceToBottom(botCol)) {
                    shortestDist = matrixSP.distanceToBottom(botCol);
                    verticalSeam = matrixSP.pathToBottom(botCol);
                }
            }
        }

        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()) {
            throw new IllegalArgumentException();
        }

        energies = createRotatedEnergies();

        picture = createRotatedPicture();

        removeVerticalSeam(seam);

        energies = createUnrotateEnergies();
        picture = createUnrotatePicture();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException();
        }

        final Picture updatedPicture = new Picture(width() - 1, height());

        for (int row = 0; row < height(); row++) {
            int col;
            for (col = 0; col < seam[row]; col++) {
                updatedPicture.set(col, row, picture.get(col, row));
            }

            for (col = seam[row] + 1; col < width(); col++) {
                updatedPicture.set(col - 1, row, picture.get(col, row));
            }
        }

        picture = updatedPicture;

        double[][] oldEnergies = energies;
        energies = new double[height()][width()];
        for (int row = 0; row < seam.length; row++) {
            System.arraycopy(oldEnergies[row], 0, energies[row], 0, seam[row]);
            if (seam[row] < width()) {
                System.arraycopy(oldEnergies[row], seam[row] + 1, energies[row], seam[row], energies[row].length - seam[row] - 1);
            }
        }

        for (int row = 0; row < seam.length; row++) {
            if (seam[row] < width()) {
                energies[row][seam[row]] = energy(seam[row], row);
            }
            if (seam[row] - 1 >= 0) {
                energies[row][seam[row] - 1] = energy(seam[row] - 1, row);
            }
        }
    }

    private Picture createRotatedPicture() {
        Picture rotatedPicture = new Picture(picture.height(), picture.width());

        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                rotatedPicture.set(y, width() - x - 1, picture.get(x, y));
            }
        }

        return rotatedPicture;
    }

    private double[][] createRotatedEnergies() {
        double[][] rotatedEnergies = new double[width()][height()];

        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                rotatedEnergies[width() - col - 1][row] = energies[row][col];
            }
        }

        return rotatedEnergies;
    }

    private Picture createUnrotatePicture() {
        Picture unrotatedPicture = new Picture(picture.height(), picture.width());

        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                unrotatedPicture.set(height() - y - 1, x, picture.get(x, y));
            }
        }

        return unrotatedPicture;
    }

    private double[][] createUnrotateEnergies() {
        double[][] unrotatedEnergies = new double[width()][height()];

        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                unrotatedEnergies[col][height() - row - 1] = energies[row][col];
            }
        }

        return unrotatedEnergies;
    }

//    public static void main(String[] args) {
//        SeamCarver seamCarver = new SeamCarver(new Picture("C:\\Users\\p.verheles\\coursera\\algorithms-sedgewick-tasks\\seam\\test-images\\chameleon.png"));
//        seamCarver.picture.show();
//        seamCarver.picture = seamCarver.createRotatedPicture();
//        seamCarver.picture.show();
//        seamCarver.picture = seamCarver.createUnrotatePicture();
//        seamCarver.picture.show();
//    }
}
