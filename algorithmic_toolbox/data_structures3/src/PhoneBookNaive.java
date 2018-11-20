import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PhoneBookNaive {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    private List<Contact> contacts = new ArrayList<>();

    private StringBuilder responseBuilder = new StringBuilder();

    private List<Query> queries = new ArrayList<>();

    public static void main(String[] args) {
        new PhoneBookNaive().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        responseBuilder.append(response);
        responseBuilder.append("\n");
        //System.out.println(response);
    }


    public String getResponse() {
        return responseBuilder.toString();
    }

    public void processQuery(Query query) {
        queries.add(query);
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            boolean wasFound = false;
            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            // otherwise, just add it
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
        } else if (query.type.equals("del")) {
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
        } else {
            String response = "not found";
            for (Contact contact: contacts)
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            writeResponse(response);
        }
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public void printQueries() {
        for(Query query : queries) {
            System.out.println(query.type + " " + query.number + (query.type.equals("add") ? " " + query.name : ""));
        }
    }
}
