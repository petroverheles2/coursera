import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();

        int[] minNumOps = new int[n + 1];
        OPS[] prevNumOps = new OPS[n + 1];
        minNumOps[1] = 0;

        for(int i = 2; i <= n; i++) {
            int currMinNumOp = Integer.MAX_VALUE;
            int multiply3NumOps = Integer.MAX_VALUE;
            int multyply2NumOps = Integer.MAX_VALUE;
            int minus1NumOps;

            int multiply3PrevNum = 0;
            int multiply2PrevNum = 0;
            int minus1PrevNum = 0;

            if(i % 3 == 0) {
                multiply3PrevNum = i / 3;
                multiply3NumOps = minNumOps[multiply3PrevNum] + 1;
            }

            if(i % 2 == 0) {
                multiply2PrevNum = i / 2;
                multyply2NumOps = minNumOps[multiply2PrevNum] + 1;
            }

            minus1PrevNum = i - 1;
            minus1NumOps = minNumOps[minus1PrevNum] + 1;

            if(multiply3NumOps < currMinNumOp) {
                currMinNumOp = multiply3NumOps;
                prevNumOps[i] = OPS.MULTIPLY_3;
            }

            if(multyply2NumOps < currMinNumOp) {
                currMinNumOp = multyply2NumOps;
                prevNumOps[i] = OPS.MULTIPLY_2;
            }

            if(minus1NumOps < currMinNumOp) {
                currMinNumOp = minus1NumOps;
                prevNumOps[i] = OPS.MINUS_1;
            }

            minNumOps[i] = currMinNumOp;
        }

        int previousNumber = n;

        while (previousNumber > 1) {
            sequence.add(previousNumber);
            switch (prevNumOps[previousNumber]) {
                case MULTIPLY_3:
                    previousNumber = previousNumber / 3;
                    break;
                case MULTIPLY_2:
                    previousNumber = previousNumber / 2;
                    break;
                case MINUS_1:
                    previousNumber = previousNumber - 1;
                    break;
            }
        }

        sequence.add(1);

        Collections.reverse(sequence);

        return sequence;
    }

    private enum OPS {
        MINUS_1, MULTIPLY_2, MULTIPLY_3;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

