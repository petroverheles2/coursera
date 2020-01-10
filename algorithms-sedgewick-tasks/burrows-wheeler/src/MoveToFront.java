import edu.princeton.cs.algs4.BinaryStdIn;
import sun.nio.cs.US_ASCII;

import java.nio.charset.Charset;
import java.util.Arrays;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] alphabet = new int[96];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = i;
        }

        //String s = BinaryStdIn.readString();
        String s = "qwerty";
        char[] input = s.toCharArray();

        int[] output = new int[input.length];

        for (int i = 0; i < output.length; i++) {
            int code = input[i] - 32;
            for (int k = 0; k < alphabet.length; k++) {
                if (code == alphabet[k]) {
                    output[i] = k;
                    break;
                }
            }

            int movedCode = output[i];
            for (int n = i; n > 0; n--) {
                alphabet[n] = alphabet[n - 1];
            }
            alphabet[0] = movedCode;
        }

        System.out.println(Arrays.toString(output));
    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}