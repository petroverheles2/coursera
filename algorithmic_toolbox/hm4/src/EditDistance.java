import java.util.*;

class EditDistance {
    public static int EditDistance(String a, String b) {
        if (a.length() == 0) {
            return b.length();
        }

        if(a.length() == 0) {
            return b.length();
        }

        if(a.equals(b)) {
            return 0;
        }

        a = " " + a;
        b = " " + b;

        int n = a.length();
        int m = b.length();

        int[][] d = new int[n][m];

        for(int i = 0; i < n; i++) {
            d[i][0] = i;
        }

        for(int j = 0; j < m; j++) {
            d[0][j] = j;
        }

        for(int j = 1; j < m; j++) {
            for(int i = 1; i < n; i++) {
                int insertion = d[i][j - 1] + 1;
                int deletion = d[i - 1][j] + 1;
                int match = d[i - 1][j - 1];
                int mismatch = d[i - 1][j - 1] + 1;

                if(a.charAt(i) == b.charAt(j)) {
                    d[i][j] = min(insertion, deletion, match);
                } else {
                    d[i][j] = min(insertion, deletion, mismatch);
                }
            }
        }

        //write your code here
        return d[n - 1][m - 1];
    }

    private static int min(int a, int b, int c) {
        int minNumber = a;

        if(b < minNumber) {
            minNumber = b;
        }

        if(c < minNumber) {
            minNumber = c;
        }

        return minNumber;
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(EditDistance(s, t));
    }

}
