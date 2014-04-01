
package com.technozor.trampoline;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static com.googlecode.catchexception.throwable.CatchThrowable.caughtThrowable;
import static com.technozor.trampoline.Bounce.*;
import java.math.BigInteger;
import static org.junit.Assert.assertFalse;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author ouertani
 */
@RunWith(JUnit4.class)
public class BounceTest {
    


    private long regFactoriel(int n) {
        if (n < 2) return 1;
        else return n * regFactoriel (n - 1);
    }
    
    private BigInteger tailRecFact(int n ) {
             return go(BigInteger.valueOf(n),BigInteger.ONE);
    }
    private BigInteger safeFactoriel(int n) {
        return (trampoline(safeGo(BigInteger.valueOf(n),BigInteger.ONE)));
    }
    
    private BigInteger go(BigInteger n, BigInteger acc) {
        if (n.intValue() < 2) return acc;
        else return go(n.subtract(BigInteger.ONE), acc.multiply(n));
    }
    
    private Bounce<BigInteger> safeGo(BigInteger n, BigInteger acc) {
        if( n.intValue() < 2) return Done(acc) ;
        else return Call( () -> safeGo(n.subtract(BigInteger.ONE) , acc.multiply(n)));
    }
  
    @Test(expected=StackOverflowError.class)
    public void testStackOverFlow() {
        regFactoriel(99900);
    }

    @Test(expected=StackOverflowError.class)
    public void regularFactoriel() {
        tailRecFact(99900) ;
    }
    
    @Test()
    public void testNoStackOverFlow() {
       
         catchThrowable(safeFactoriel(99900));
         assertFalse (caughtThrowable() instanceof StackOverflowError) ;
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    
    
}
