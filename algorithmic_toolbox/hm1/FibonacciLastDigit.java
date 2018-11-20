import java.util.Scanner;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigit(int n) {
        if(n <= 1) {
            return n;
        }

        int fiminus2 = 0;
        int fiminus1 = 1;

        int fi = 0;
        for(int i = 2; i <= n; i++) {
            fi = (fiminus1 + fiminus2) % 10;
            fiminus2 = fiminus1;
            fiminus1 = fi;
        }

        return fi;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}

