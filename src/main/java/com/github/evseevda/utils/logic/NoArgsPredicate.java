package com.github.evseevda.utils.logic;

import java.util.Objects;

/**
 * Represents a predicate (boolean-valued function) of zero arguments.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test()}.
 *
 * @see LazyLogicalExpression
 * @since 0.9.0
 * @author EvseevDA
 */
@FunctionalInterface
public interface NoArgsPredicate {

    /**
     * Evaluates this predicate.
     *
     * @return {@code true} if the result of this predicate is {@code true},
     * otherwise {@code false}
     */
    boolean test();

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default NoArgsPredicate and(NoArgsPredicate other) {
        Objects.requireNonNull(other);
        return () -> this.test() && other.test();
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default NoArgsPredicate or(NoArgsPredicate other) {
        Objects.requireNonNull(other);
        return () -> this.test() || other.test();
    }

    /**
     * Returns a composed predicate that represents the XOR of this predicate and another.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be XORed with this
     *              predicate
     * @return a composed predicate that represents the
     * XOR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default NoArgsPredicate xor(NoArgsPredicate other) {
        Objects.requireNonNull(other);
        return () -> this.test() ^ other.test();
    }

    /**
     * Returns a predicate that is the negation of the supplied predicate.
     *
     * @param target  predicate to negate
     *
     * @return a predicate that negates the results of the supplied
     *         predicate
     *
     * @throws NullPointerException if target is null
     *
     */
    static NoArgsPredicate not(NoArgsPredicate target) {
        Objects.requireNonNull(target);
        return () -> !target.test();
    }

}
