/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ch.zhaw.thin.upn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void test() throws InterruptedException {
        final PushdownAutomata pushdownAutomata = new PushdownAutomata();
        assertEquals("6664", pushdownAutomata.calculate(false, "34+62+89+43+***"));
        assertEquals("58", pushdownAutomata.calculate(false, "31+78+987+1214++7++++++"));

        assertThrows(IllegalStateException.class, () -> pushdownAutomata.calculate(false, "34+*"));
        assertThrows(IllegalStateException.class, () -> pushdownAutomata.calculate(false, "8+9+7*2*"));
    }
}
