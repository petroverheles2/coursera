import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        byte[] alphabet = createAlphabet();

        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        for (int i = 0; i < input.length; i++) {
            byte index = 0;
            for (int k = 0; k < alphabet.length; k++) {
                if (input[i] == alphabet[k]) {
                    index = (byte)k;
                    BinaryStdOut.write(index);
                    break;
                }
            }

            moveElementToFront(alphabet, index);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        byte[] alphabet = createAlphabet();

        do {
            int index = BinaryStdIn.readByte();
            BinaryStdOut.write(alphabet[index]);
            moveElementToFront(alphabet, index);
        } while (!BinaryStdIn.isEmpty());

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private static byte[] createAlphabet() {
        byte[] alphabet = new byte[256];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (byte)i;
        }
        return alphabet;
    }

    private static void moveElementToFront(byte[] arr,
                                           int index) {
        byte code = arr[index];
        for (int n = index; n > 0; n--) {
            arr[n] = arr[n - 1];
        }
        arr[0] = code;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}