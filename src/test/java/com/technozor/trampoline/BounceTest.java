
package com.technozor.trampoline;

import static com.technozor.trampoline.Bounce.*;
import java.math.BigInteger;
import java.util.function.BinaryOperator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author ouertani
 */
@RunWith(JUnit4.class)
public class BounceTest {
    
    public BounceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private long regFactoriel(long n) {
        if (n < 2) return 1;
        else return n * regFactoriel (n - 1);
    }
    
    private BigInteger tailRecFact(long n ) {
             return go(BigInteger.valueOf(n),BigInteger.ONE);
    }
    private BigInteger safeFactoriel(long n) {
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
    
    @Test
    public void testNoStackOverFlow() {
       
        BigInteger calculated = safeFactoriel(99900);
        BigInteger expected = BigInteger.valueOf(6);
        
        assertEquals(expected, calculated );
    }

    
    
}
