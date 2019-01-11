import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private final Picture picture;

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

        int rx = picture.get(x - 1, y).getRed() - picture.get(x + 1, y).getRed();
        int gx = picture.get(x - 1, y).getGreen() - picture.get(x + 1, y).getGreen();
        int bx = picture.get(x - 1, y).getBlue() - picture.get(x + 1, y).getBlue();

        int ry = picture.get(x, y - 1).getRed() - picture.get(x, y + 1).getRed();
        int gy = picture.get(x, y - 1).getGreen() - picture.get(x, y + 1).getGreen();
        int by = picture.get(x, y - 1).getBlue() - picture.get(x, y + 1).getBlue();

        int deltaSqrX = rx * rx + gx * gx + bx * bx;
        int deltaSqrY = ry * ry + gy * gy + by * by;

        return Math.sqrt(deltaSqrX + deltaSqrY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
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
    }
}
