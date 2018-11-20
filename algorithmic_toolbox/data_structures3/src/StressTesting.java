import java.security.SecureRandom;
import java.util.Random;

/**
/**
 * Created by petroverheles on 7/19/16.
 */
public class StressTesting {
    static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomString( int len ) {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) {
            sb.append( AB.charAt( rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        for(int k = 0; k < 20000; k++) {
            if(!singleTest()) {
                break;
            }
        }
    }

    private static boolean singleTest() {
        String[] commands = {"add", "find", "del"};
        Random random = new Random();
        int queriesNumber = random.nextInt(100);
        int[] numbers = new int[random.nextInt(20) + 1];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10000000);
        }
        PhoneBookNaive phoneBookNaive = new PhoneBookNaive();
        PhoneBookBetter phoneBook = new PhoneBookBetter();
        for(int i = 0; i < queriesNumber; i++) {
            String command = commands[random.nextInt(3)];
            String name = randomString(random.nextInt(15) + 1);
            int number = numbers[random.nextInt(numbers.length)];
            PhoneBookNaive.Query naiveQuery;
            PhoneBookBetter.Query query;
            if(command.equals("add")) {
                naiveQuery = new PhoneBookNaive.Query(command, name, number);
                query = new PhoneBookBetter.Query(command, name, number);
            } else {
                naiveQuery = new PhoneBookNaive.Query(command, number);
                query = new PhoneBookBetter.Query(command, number);
            }

            phoneBookNaive.processQuery(naiveQuery);
            phoneBook.processQuery(query);
        }

        if(!phoneBookNaive.getResponse().equals(phoneBook.getResponse())) {
            phoneBookNaive.printQueries();
            System.out.println();
            System.out.println(phoneBookNaive.getResponse());
            System.out.println();
            System.out.println(phoneBook.getResponse());
            return false;
        }

        return true;
    }
}
