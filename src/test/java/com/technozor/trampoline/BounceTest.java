
package com.technozor.trampoline;

import static com.technozor.trampoline.Bounce.*;
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
    
    private long tailRecFact(long n ) {
     final BinaryOperator<Long> go  = (t , acc) -> {
         if (t < 2) return acc ;
         else return go.apply(t-1,acc *t) ;
     };
             return go.apply(n,1L);
    }
    
    
    private Bounce<Long> tramFactoriel(long n) {
        if (n < 2) return Done(1L) ;
        else return Call( () -> {
            return Done(n * trampoline(tramFactoriel(n -1)));
        });
    }
    /**
     * Test of asDone method, of class Bounce.
     */
    @Test(expected=StackOverflowError.class)
    public void testStackOverFlow() {
        regFactoriel(10000);
    }
    
    @Test
    public void testNoStackOverFlow() {
        Bounce<Long> bounce = tramFactoriel(10000);
        Long calculated = trampoline(bounce);
        Long expected = 10000000L;
        
        assertEquals(expected, calculated );
    }

    
    
}
