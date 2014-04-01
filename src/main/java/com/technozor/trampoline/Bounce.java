
package com.technozor.trampoline;

import java.util.function.Supplier;

/**
 *
 * @author ouertani
 * @param <A>
 */
public interface Bounce<A> {
    
    
    public static  <A> Done<A> Done(A result) { return new Done(result); }
    public static  <A> Call<A> Call(Supplier<Bounce<A>> thunk) { return new Call(thunk); }
    
    public Boolean isDone();
    public static <A> A trampoline(final Bounce<A> bounce) {
        Bounce _bounce = bounce;
        while(!_bounce.isDone())
           _bounce = ((Call<A>) _bounce).thunk();

            return((Done<A>) _bounce).thunk();
    }
    
    class Done<A> implements Bounce<A> {
       private final A result;
       public Done(A result) {
           this.result = result;
       }
       public A thunk() {return result; }
       
       @Override
       public Boolean isDone(){return true ; }
    } 
   
   class Call<A> implements Bounce<A> {
       private final Supplier<Bounce<A>> thunk;
       public Call(Supplier<Bounce<A>> thunk) {
           this.thunk =thunk;
       }
       public Bounce<A> thunk() { return thunk.get(); }
       @Override
       public Boolean isDone() { return false ; }
   }
}
