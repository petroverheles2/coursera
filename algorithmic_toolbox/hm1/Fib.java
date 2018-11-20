import java.util.Scanner;

public class Fib {
    private static long calc_fib(int n) {
        if (n > 45 || n < 0) {
            throw new IllegalArgumentException();
        }

        if(n <= 1) {
            return n;
        }

        int fiminus2 = 0;
        int fiminus1 = 1;

        int fi = 0;
        for(int i = 2; i <= n; i++) {
            fi = fiminus1 + fiminus2;
            fiminus2 = fiminus1;
            fiminus1 = fi;
        }

        return fi;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calc_fib(n));
    }
}
