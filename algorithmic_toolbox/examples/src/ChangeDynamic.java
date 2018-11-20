import java.util.Scanner;

public class ChangeDynamic {
    private static int getChange(int n) {
        int[] coins = {20, 8, 1};

        int[] minNumCoins = new int[n + 1];
        minNumCoins[0] = 0;

        for(int m = 1; m <= n; m++) {
            minNumCoins[m] = Integer.MAX_VALUE;
            for(int i = 0; i < coins.length; i++) {
                if(m >= coins[i]) {
                    int numCoins = minNumCoins[m - coins[i]] + 1;
                    if(numCoins < minNumCoins[m]) {
                        minNumCoins[m] = numCoins;
                    }
                }
            }
        }

        return minNumCoins[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(getChange(n));

    }
}

