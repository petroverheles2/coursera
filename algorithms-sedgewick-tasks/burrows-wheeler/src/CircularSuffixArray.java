import static java.util.Arrays.binarySearch;

import java.util.Arrays;

public class CircularSuffixArray {

    private String s;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.s = s;
        this.index = new int[length()];

        String[] suffixes = new String[s.length()];
        suffixes[0] = s;
        for (int i = 1; i < length(); i++) {
            suffixes[i] = suffixes[i - 1].substring(1) + suffixes[i - 1].charAt(0);
        }
        String[] sortedSuffixes = Arrays.copyOf(suffixes, suffixes.length);

        Arrays.sort(sortedSuffixes);

        for (int i = 0; i < suffixes.length; i++) {
            int sortedIndex = binarySearch(sortedSuffixes, suffixes[i]);
            index[sortedIndex] = i;
        }
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(circularSuffixArray.index(11));
    }

}