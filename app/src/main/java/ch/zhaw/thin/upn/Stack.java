package ch.zhaw.thin.upn;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.iterate;

import java.util.Objects;

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

    public String getElementAtCurrentPosition() {
        return stack[position];
    }

    public boolean containsOnlyResult() {
        return stream(stack)
            .filter(Objects::nonNull)
            .filter(entry -> !FIRST_STACK_CHAR.equals(entry))
            .count() == 1;
    }

    @Override
    public String toString() {
        return iterate(position, i -> i >= 0, i -> i - 1)
            .mapToObj(i -> stack[i])
            .collect(joining(" "));
    }
}
