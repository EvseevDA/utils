package com.github.evseevda.utils.logic;

import java.util.Objects;

public class LazyLogicalExpression {

    private NoArgsPredicate finalPredicate;

    private LazyLogicalExpression(NoArgsPredicate root) {
        Objects.requireNonNull(root);
        finalPredicate = root;
    }

    private LazyLogicalExpression(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);
        finalPredicate = expression.finalPredicate;
    }

    public static LazyLogicalExpression expr(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);
        return new LazyLogicalExpression(predicate);
    }

    public static LazyLogicalExpression expr(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);
        return new LazyLogicalExpression(expression);
    }

    public LazyLogicalExpression and(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.and(predicate);
        return this;
    }

    public LazyLogicalExpression and(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.and(expression.finalPredicate);
        return this;
    }

    public LazyLogicalExpression or(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.or(predicate);
        return this;
    }

    public LazyLogicalExpression or(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.or(expression.finalPredicate);
        return this;
    }

    public LazyLogicalExpression xor(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.xor(predicate);
        return this;
    }

    public LazyLogicalExpression xor(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.xor(expression.finalPredicate);
        return this;
    }

    public static LazyLogicalExpression not(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);
        return expr(NoArgsPredicate.not(predicate));
    }

    public static LazyLogicalExpression not(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);
        return expr(NoArgsPredicate.not(expression.finalPredicate));
    }

    public boolean result() {
        return finalPredicate.test();
    }

}
