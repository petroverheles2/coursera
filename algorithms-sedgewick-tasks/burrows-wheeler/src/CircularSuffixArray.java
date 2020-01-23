import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private final String s;
    private final int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        this.s = s;
        this.index = new int[length()];

        if (s.isEmpty()) {
            return;
        }

        String[] suffixes = new String[s.length()];
        suffixes[0] = s + 0;
        for (int i = 1; i < length(); i++) {
            suffixes[i] = suffixes[i - 1].substring(1, length()) + suffixes[i - 1].charAt(0) + i;
        }
        String[] sortedSuffixes = Arrays.copyOf(suffixes, suffixes.length);

        Arrays.sort(sortedSuffixes, Comparator.comparing(suff -> suff.substring(0, length())));

        for (int i = 0; i < sortedSuffixes.length; i++) {
            index[i] = Integer.parseInt(sortedSuffixes[i].substring(length()));
        }
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > length() - 1) {
            throw new IllegalArgumentException();
        }

        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(circularSuffixArray.index(11));
    }

}