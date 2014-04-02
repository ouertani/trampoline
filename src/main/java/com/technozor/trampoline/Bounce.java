package com.technozor.trampoline;

import java.util.function.Supplier;

/**
 * @param <A>
 * @author ouertani
 */
public interface Bounce<E> {


    public static <A> Bounce<A> Done(A thunk) {
        return   new Done(thunk);
    }

    public static <A> Bounce<A> Call(Supplier<Bounce<A>> thunk) {
        return new Call(thunk);
    }

    public static <A> A trampoline(final Bounce<A> bounce) {
        Bounce<A> _bounce = bounce;
        while (_bounce.hasNext())
            _bounce = ((Call<A>) _bounce).thunk();

        return ((Done<A>) _bounce).thunk();
    }

    public default boolean hasNext() {
        return true;
    }

    class Done<A> implements Bounce<A> {
        private final A thunk;

        private Done(A thunk) {
            this.thunk = thunk;
        }

        public A thunk() {
            return thunk;
        }

        @Override
        public boolean hasNext() {
            return false;
        }
    }

    class Call<A> implements Bounce<A> {
        private final Supplier<Bounce<A>> thunk;

        private Call(Supplier<Bounce<A>> thunk) {
            this.thunk = thunk;
        }

        public Bounce<A> thunk() {
            return thunk.get();
        }
    }
}
