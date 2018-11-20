import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> stack = new Stack<Bracket>();
        int unmatchedPosition = 0;
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                stack.push(new Bracket(next, position + 1));
            }

            if (next == ')' || next == ']' || next == '}') {
                if(stack.isEmpty() || !stack.pop().match(next)) {
                    unmatchedPosition = position + 1;
                    break;
                }
            }
        }

        if(unmatchedPosition > 0) {
            System.out.println(unmatchedPosition);
        } else if(stack.isEmpty()) {
            System.out.println("Success");
        } else {
            System.out.println(stack.pop().position);
        }
    }
}
