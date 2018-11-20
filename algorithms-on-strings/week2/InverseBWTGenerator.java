import java.util.Random;

/**
 * Created by petroverheles on 5/26/17.
 */
public class InverseBWTGenerator {
    public static void main(String args[]) {
        char[] abc = new char[4];
        abc[0] = 'A';
        abc[1] = 'C';
        abc[2] = 'G';
        abc[3] = 'T';

        int length = 100000;
        Random r = new Random();

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < length - 1; i++) {
            result.append(abc[r.nextInt(abc.length)]);
        }

        result.insert(r.nextInt(length), '$');

        System.out.print(result.toString());
    }
}
