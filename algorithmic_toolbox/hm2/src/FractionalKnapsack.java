import java.util.Scanner;

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double result = 0;

        double capacityRemainder = capacity;

        for(int i = 0; i < values.length; i++) {

            if(capacityRemainder < 0.001) {
                break;
            }

            int indexOfMaxFraction = 0;
            double maxFraction = (double)values[indexOfMaxFraction] / weights[indexOfMaxFraction];

            for(int k = 1; k < values.length; k++) {
                double nextFraction = (double)values[k] / weights[k];
                if(nextFraction > maxFraction) {
                    indexOfMaxFraction = k;
                    maxFraction = nextFraction;
                }
            }

            if(weights[indexOfMaxFraction] < capacityRemainder) {
                capacityRemainder -= weights[indexOfMaxFraction];
                result += values[indexOfMaxFraction];
                values[indexOfMaxFraction] = 0;
            } else {
                result += values[indexOfMaxFraction] * capacityRemainder / weights[indexOfMaxFraction];
                capacityRemainder = 0;
            }
        }

        return result;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
} 
