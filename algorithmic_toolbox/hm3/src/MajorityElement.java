import java.util.*;
import java.io.*;

public class MajorityElement {
    private static int getMajorityElement(int[] a) {
        if(a.length == 1) {
            return 1;
        }

        new Mergesort().sort(a);

        int currentElement = a[0];
        int currentCount = 1;
        int maxCountElement = currentElement;
        int maxCount = currentCount;

        for(int i = 1; i < a.length; i++) {
            if(currentElement == a[i]) {
                currentCount++;
            } else {
                if(currentCount > maxCount) {
                    maxCount = currentCount;
                    maxCountElement = currentElement;
                }
                currentElement = a[i];
                currentCount = 1;
            }
        }

        if(currentCount > maxCount) {
            maxCount = currentCount;
            maxCountElement = currentElement;
        }

        if(maxCount > (a.length / 2)) {
            return maxCountElement;
        }

        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static class Mergesort {
        private int[] numbers;
        private int[] helper;

        private int number;

        public void sort(int[] values) {
            this.numbers = values;
            number = values.length;
            this.helper = new int[number];
            mergesort(0, number - 1);
        }

        private void mergesort(int low, int high) {
            // check if low is smaller then high, if not then the array is sorted
            if (low < high) {
                // Get the index of the element which is in the middle
                int middle = low + (high - low) / 2;
                // Sort the left side of the array
                mergesort(low, middle);
                // Sort the right side of the array
                mergesort(middle + 1, high);
                // Combine them both
                merge(low, middle, high);
            }
        }

        private void merge(int low, int middle, int high) {

            // Copy both parts into the helper array
            for (int i = low; i <= high; i++) {
                helper[i] = numbers[i];
            }

            int i = low;
            int j = middle + 1;
            int k = low;
            // Copy the smallest values from either the left or the right side back
            // to the original array
            while (i <= middle && j <= high) {
                if (helper[i] <= helper[j]) {
                    numbers[k] = helper[i];
                    i++;
                } else {
                    numbers[k] = helper[j];
                    j++;
                }
                k++;
            }
            // Copy the rest of the left side of the array into the target array
            while (i <= middle) {
                numbers[k] = helper[i];
                k++;
                i++;
            }

        }
    }

}

