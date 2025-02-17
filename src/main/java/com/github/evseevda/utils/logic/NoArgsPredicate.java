package com.github.evseevda.utils.logic;

import java.util.Objects;

@FunctionalInterface
public interface NoArgsPredicate {

    boolean test();

    default NoArgsPredicate and(NoArgsPredicate that) {
        Objects.requireNonNull(that);
        return () -> this.test() && that.test();
    }

    default NoArgsPredicate or(NoArgsPredicate that) {
        Objects.requireNonNull(that);
        return () -> this.test() || that.test();
    }

    default NoArgsPredicate xor(NoArgsPredicate that) {
        Objects.requireNonNull(that);
        return () -> this.test() ^ that.test();
    }

    static NoArgsPredicate not(NoArgsPredicate that) {
        Objects.requireNonNull(that);
        return () -> !that.test();
    }

}
