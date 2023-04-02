package ch.zhaw.thin.upn;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PushdownAutomata {

    private static final Character[] VALID_OPERATORS = new Character[2];
    private static final char PLUS_SIGN = '+';
    private static final char MULTIPLICATION_SIGN = '*';

    static {
        VALID_OPERATORS[0] = PLUS_SIGN;
        VALID_OPERATORS[1] = MULTIPLICATION_SIGN;
    }

    public String calculate(final boolean stepMode, final String input)
        throws InterruptedException {
        final Stack stack = new Stack();
        final char[] chars = input.toCharArray();
        final StringBuilder remainingChars = new StringBuilder(input);

        if (stepMode) {
            printCalculationStep(stack, remainingChars);
        }

        for (char currentChar : chars) {
            if (isDigit(currentChar)) {
                stack.push(valueOf(currentChar));
            } else if (isValidOperator(currentChar)) {
                if (!containsTwoPreviousNumbers(stack)) {
                    throw new IllegalStateException("Invalid calculation");
                }

                performCalculation(stack, currentChar);
            } else {
                throw new IllegalStateException(format("Invalid char %s provided", currentChar));
            }

            if (stepMode) {
                SECONDS.sleep(1);
                remainingChars.deleteCharAt(0);
                printCalculationStep(stack, remainingChars);
            }
        }

        return stack.getElementAtCurrentPosition();
    }

    private void printCalculationStep(Stack stack, StringBuilder remainingChars) {
        System.out.println("("+ remainingChars + ", " + stack + ") |- ");
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
}
