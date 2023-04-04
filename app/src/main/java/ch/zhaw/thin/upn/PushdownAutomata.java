package ch.zhaw.thin.upn;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Iterator;
import java.util.List;

public class PushdownAutomata {

    private static final Character[] VALID_OPERATORS = new Character[2];
    private static final char PLUS_SIGN = '+';
    private static final char MULTIPLICATION_SIGN = '*';

    static {
        VALID_OPERATORS[0] = PLUS_SIGN;
        VALID_OPERATORS[1] = MULTIPLICATION_SIGN;
    }

    public void calculate(final boolean stepMode, final String input)
        throws InterruptedException {
        final Stack stack = new Stack();
        final List<Character> inputChars = input.chars().mapToObj(c -> (char) c).toList();
        final StringBuilder remainingChars = new StringBuilder(input);
        boolean isValid = true;

        if (stepMode) {
            printCalculationStep(stack, remainingChars);
        }

        final Iterator<Character> iterator = inputChars.iterator();
        while (iterator.hasNext() && isValid) {
            final Character nextChar = iterator.next();
            if (isDigit(nextChar)) {
                stack.push(valueOf(nextChar));
            } else if (isValidOperator(nextChar)) {
                if (containsTwoPreviousNumbers(stack)) {
                    performCalculation(stack, nextChar);
                } else {
                    isValid = false; // Every operator must have at least two numbers as predecessors
                }
            } else {
                isValid = false; // Contains a character outside the defined alphabet
            }
            if (stepMode) {
                SECONDS.sleep(1);
                remainingChars.deleteCharAt(0);
                printCalculationStep(stack, remainingChars);
            }
        }

        if (!stack.containsOnlyResult()) {
            isValid = false; // Stack is not empty, not enough operators were given
        }

        printResult(input, stack, isValid);
    }

    private void printCalculationStep(Stack stack, StringBuilder remainingChars) {
        System.out.println("(" + remainingChars + ", " + stack + ") |- ");
    }

    private boolean isValidOperator(char currentChar) {
        return stream(VALID_OPERATORS)
            .anyMatch(validChar -> currentChar == validChar);
    }

    private boolean containsTwoPreviousNumbers(Stack stack) {
        try {
            parseInt(stack.spyPeekWithOffset(0));
            parseInt(stack.spyPeekWithOffset(1));
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private void performCalculation(Stack stack, char currentChar) {
        final int num1 = parseInt(stack.pop());
        final int num2 = parseInt(stack.pop());

        if (currentChar == PLUS_SIGN) {
            stack.push(valueOf(num1 + num2));
        } else if (currentChar == MULTIPLICATION_SIGN) {
            stack.push(valueOf(num1 * num2));
        }
    }

    private void printResult(String input, Stack stack, boolean isValid) {
        System.out.println(isValid
            ? "Accepted with result: " + stack.getElementAtCurrentPosition()
            : format("Input word %s was discarded.", input));
    }

}
