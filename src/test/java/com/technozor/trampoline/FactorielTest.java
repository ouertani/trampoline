package com.technozor.trampoline;

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
public class FactorielTest {

    private long regularFactoriel(int n) {
        if (n < 2) return 1;
        else return n * regularFactoriel(n - 1);
    }

    @Test(expected = StackOverflowError.class)
    public void testStackOverFlow() {
        regularFactoriel(99900);
    }

    private BigInteger tailRecFact(int n) {
        return go(n, BigInteger.ONE);
    }


    private BigInteger go(int n, BigInteger acc) {
        if (n < 2) return acc;
        else return go(n - 1, acc.multiply(BigInteger.valueOf(n)));
    }


    @Test(expected = StackOverflowError.class)
    public void regularFactoriel() {
        tailRecFact(99900);
    }


    private BigInteger safeFactoriel(int n) {
        return trampoline(safeGo(n, BigInteger.ONE));
    }

    private Bounce<BigInteger> safeGo(int n, BigInteger acc) {
        if (n < 2) return Done(acc);
        else return Call(() -> safeGo(n - 1, acc.multiply(BigInteger.valueOf(n))));
    }


    @Test()
    public void testNoStackOverFlow() {

        BigInteger safeFactoriel = safeFactoriel(99900);

        assertTrue(safeFactoriel instanceof BigInteger);
    }
}
