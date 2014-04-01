package com.technozor.trampoline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.technozor.trampoline.Bounce.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author ouertani
 */
@RunWith(JUnit4.class)
public class EvenOddTest {

    public boolean isOdd(long n) {
        if (n == 0) return false;
        else return isEven(n - 1);

    }

    public boolean isEven(long n) {
        if (n == 0) return true;
        else return isOdd(n - 1);
    }

    @Test(expected = StackOverflowError.class)
    public void testStackOverFlow() {
        isEven(99900);
    }


    public Bounce<Boolean> safeOdd(long n) {
        if (n == 0) return Done(false);
        else return Call(() -> safeEven(n - 1));
    }

    public Bounce<Boolean> safeEven(long n) {
        if (n == 0) return Done(true);
        else return Call(() -> safeOdd(n - 1));
    }

    @Test()
    public void testNoStackOverFlow() {

        boolean isEven = trampoline(safeEven(99900));

        assertTrue(isEven);

        boolean isOdd = trampoline(safeEven(99901));

        assertFalse(isOdd);
    }
}
