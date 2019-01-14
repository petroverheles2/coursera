import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
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
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
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
        double[][] energies = new double[width()][height()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                energies[col][row] = energy(col, row);
            }
        }

        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()) {
            throw new IllegalArgumentException();
        }

        Picture updatedPicture = new Picture(width() - 1, height() - 1);

        for (int col = 0; col < width(); col++) {
            int row;
            for (row = 0; row < seam[col]; row++) {
                updatedPicture.set(col, row, picture.get(col, row));
            }

            for (row = seam[col] + 1; row < height(); row++) {
                updatedPicture.set(col, row - 1, picture.get(col, row));
            }
        }

        picture = updatedPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException();
        }

        Picture updatedPicture = new Picture(width() - 1, height() - 1);

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
    }
}
