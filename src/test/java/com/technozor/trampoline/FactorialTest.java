package com.technozor.trampoline;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import static com.technozor.trampoline.Bounce.*;
import static org.junit.Assert.assertTrue;

/**
 * @author ouertani
 */
@RunWith(JUnit4.class)
public class FactorialTest {
    private final int N = 99900;

    private long regularFactorial(int n) {
        if (n < 2) return 1;
        else return n * regularFactorial(n - 1);
    }

    @Test(expected = StackOverflowError.class)
    public void testRegularFactorialStackOverFlow() {
        regularFactorial(N);
    }

    private BigInteger go(int n, BigInteger acc) {
        if (n < 2) return acc;
        else return go(n - 1, acc.multiply(BigInteger.valueOf(n)));
    }


    @Test(expected = StackOverflowError.class)
    public void regularFactorial() {
        go(N, BigInteger.ONE);
    }


    private Bounce<BigInteger> safeGo(int n, BigInteger acc) {
        if (n < 2) return Done(acc);
        else return Call(() -> safeGo(n - 1, acc.multiply(BigInteger.valueOf(n))));
    }

    @Test()
    public void testNoStackOverFlow() {

        BigInteger safe =  trampoline(safeGo(N, BigInteger.ONE));

        assertTrue(safe instanceof BigInteger);
    }
}
