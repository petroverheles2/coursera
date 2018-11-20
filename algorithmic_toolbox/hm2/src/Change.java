import java.util.Scanner;

public class Change {
    private static int getChange(int n) {
        int[] denominations = {10, 5, 1};
        int coinsNumber = 0;
        int remainder = n;
        for(int i = 0; i < denominations.length; i++) {
            coinsNumber += remainder / denominations[i];
            remainder = remainder % denominations[i];
        }
        return coinsNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(getChange(n));

    }
}

