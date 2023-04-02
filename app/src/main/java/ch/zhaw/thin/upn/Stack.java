package ch.zhaw.thin.upn;

public class Stack {

    private static final int MAX_CHAR_LENGTH = 100;
    private static final String FIRST_STACK_CHAR = "$";

    private final String[] stack;
    private int position;

    public Stack() {
        stack = new String[MAX_CHAR_LENGTH];
        stack[0] = FIRST_STACK_CHAR;
        position = 0;
    }

    public void push(String value) {
        stack[++position] = value;
    }

    public String pop() {
        if (position == 0) {
            return FIRST_STACK_CHAR;
        }

        final String poppedString = stack[position];
        stack[position] = null;
        position--;
        return poppedString;
    }

    public String spyPeekWithOffset(int offset){
        return stack[position - offset];
    }

    public boolean isEmpty() {
        return position == 0 && stack[position].equals(FIRST_STACK_CHAR);
    }

    public String getElementAtCurrentPosition() {
        return stack[position];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = position; i >= 0 ; i--) {
            sb.append(stack[i]);
        }

        return sb.toString();
    }
}
