import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();

        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);

        for (int i = 0; i < circularSuffixArray.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int charAt = circularSuffixArray.index(i);
            charAt = charAt == 0 ? circularSuffixArray.length() - 1 : charAt - 1;
            BinaryStdOut.write((byte) s.charAt(charAt));
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();

        // reading input and reconstructing first and last column of sorted circular suffixes table
        String s = BinaryStdIn.readString();
        char[] lastColumn = s.toCharArray();
        char[] firstColumn = sortByCount(Arrays.copyOf(lastColumn, lastColumn.length));

        // detecting start indexes of same char sequences in the first (sorted) column
        int[] starts = new int[256];
        char nextChar = firstColumn[0];
        starts[nextChar] = 0;
        for (int i = 1; i < firstColumn.length; i++) {
            if (nextChar == firstColumn[i]) {
                continue;
            }

            nextChar = firstColumn[i];
            starts[nextChar] = i;
        }

        // constructing next array
        int[] next = new int[lastColumn.length];
        int[] counts = new int[256];
        for (int i = 0; i < lastColumn.length; i++) {
            next[starts[lastColumn[i]] + counts[lastColumn[i]]] = i;
            counts[lastColumn[i]]++;
        }

        // final step - constructing original string basing on next array
        char[] original = new char[lastColumn.length];
        int current = next[0];
        for (int i = 0; i < original.length; i++) {
            original[i] = firstColumn[current];
            current = next[current];
        }

        BinaryStdOut.write(new String(original));

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private static char[] sortByCount(char[] t) {
        char[] tSorted = new char[t.length];
        int[] count = new int[256];

        for (int i = 0; i < t.length; i++) {
            count[t[i]] = count[t[i]] + 1;
        }

        int shift = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                continue;
            }

            int n = 0;
            for (; n < count[i]; n++) {
                tSorted[shift + n] = (char) i;
            }
            shift += n;
        }

        return tSorted;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}