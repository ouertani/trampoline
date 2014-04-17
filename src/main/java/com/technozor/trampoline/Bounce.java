package com.technozor.trampoline;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @param <A>
 * @author ouertani
 */
@FunctionalInterface
public interface Bounce<E> {

    public Bounce<E> next();

    public static <A> Bounce<A> Done(A thunk) {
        return   new Done(thunk);
    }

    public static <A> Bounce<A> Call(Supplier<Bounce<A>> thunk) {
        return new Call(thunk);
    }

    public static <A> A trampoline(final Bounce<A> bounce) {
        return Stream.iterate(bounce, Bounce::next)
                .filter(Bounce::terminated)
                .findFirst().map(x -> (Done<A>)x)
                .get().thunk;
    }

    public default boolean terminated() {
        return false;
    }

    class Done<A> implements Bounce<A> {
        private final A thunk;

        private Done(A thunk) {
            this.thunk = thunk;
        }

        @Override
        public boolean terminated() {
            return true;
        }

        public Bounce<A> next() { throw new Error("Don't call"); }
    }

    class Call<A> implements Bounce<A> {
        private final Supplier<Bounce<A>> thunk;

        private Call(Supplier<Bounce<A>> thunk) {
            this.thunk = thunk;
        }

        public Bounce<A> next() {
            return thunk.get();
        }
    }
}
