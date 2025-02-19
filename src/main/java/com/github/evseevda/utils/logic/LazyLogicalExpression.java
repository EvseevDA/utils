package com.github.evseevda.utils.logic;

import java.util.Arrays;
import java.util.Objects;

/**
 * The main goal of this class is to provide the ability to create lazy logical expressions.
 * <br>In addition, the goal pursued by the {@code EagerLogicalExpression} class is also fulfilled,
 * which consists of replacing native Java logical operators (&&, ||, ^, !) with English equivalent words.
 * <br>Expressions passed to the methods of this class <b>are not calculated immediately</b>,
 * but only during the call of the {@code result()} method, so the client of this class gets the
 * opportunity to control the moment when the expression is actually calculated.
 * @since 0.9.0
 * @see EagerLogicalExpression
 * @see com.github.evseevda.LazyLogicalExpressionUsageExamples
 * @author EvseevDA
 */
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

    /**
     * Creates a new logical expression.
     * <br> Calling this method is equivalent to natively putting
     * parentheses around the expression.
     * <br> If expr is called once in a method call chain of this class,
     * the logical operators are called in direct order and in turn.
     * <blockquote>
     * <pre>
     *      {@code expr(someClass::condition1).and(someClass::condition2).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() && someClass.condition2()}
     * </pre></blockquote>
     * <br> If expr occurs twice, the results of the expressions written before the second expr
     * will be calculated first, then the results of the expressions after the second expr
     * will be calculated, and then the results of the expressions will be combined.
     * When expr occurs more than twice, the situation is similar.
     * <blockquote>
     * <pre>
     *      {@code expr(someClass::condition1).and(someClass::condition2).or(expr(someClass::condition3).or(someClass::condition4)).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code (someClass.condition1() && someClass.condition2()) || (someClass.condition3() || someClass.condition4())}
     * </pre></blockquote>
     *
     * @param predicate the first expression in a chain of expressions
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static LazyLogicalExpression expr(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);
        return new LazyLogicalExpression(predicate);
    }

    /**
     * In general, it is similar to expr with a NoArgsPredicate parameter, but now the main expression is another expression.
     * <br> This method is needed to create nested expressions.
     * <br> Example:
     * <blockquote>
     * <pre>
     *      {@code expr(someClass::condition1).and(expr(expr(someClass::condition2).or(someClass::condition3).and(not(someClass::condition4)))).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() && ((someClass.condition2() || someClass.condition3()) && !someClass.condition4())}
     * </pre></blockquote>
     *
     * @param expression the first expression in a chain of expressions
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static LazyLogicalExpression expr(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);
        return new LazyLogicalExpression(expression);
    }

    /**
     * Aggregates all passed predicates with logical AND.
     * <br> Example:
     * <blockquote>
     * <pre>
     *      {@code allOf(List.of(someClass::condition1, someClass::condition2, someClass::condition3)).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() && someClass.condition2() && someClass.condition3()}
     * </pre></blockquote>
     *
     * @param predicates which will be aggregated
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions.
     * <b><h3>If {@code predicates} is empty
     * then returned expression will contain {@code true}.</h3>
     */
    public static LazyLogicalExpression allOf(Iterable<NoArgsPredicate> predicates) {
        validatePredicatesChain(predicates);

        LazyLogicalExpression expression = new LazyLogicalExpression(() -> true);
        predicates.forEach(expression::and);

        return expression;
    }

    /**
     * Aggregates all passed predicates with logical AND.
     * <br> Example:
     * <blockquote>
     * <pre>
     *      {@code allOf(someClass::condition1, someClass::condition2, someClass::condition3).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() && someClass.condition2() && someClass.condition3()}
     * </pre></blockquote>
     *
     * @param predicates which will be aggregated
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions.
     * <b><h3>If {@code predicates} is empty
     * then returned expression will contain {@code true}.</h3>
     */
    public static LazyLogicalExpression allOf(NoArgsPredicate... predicates) {
        return allOf(Arrays.asList(predicates));
    }

    /**
     * Aggregates all passed predicates with logical OR.
     * <br> Example:
     * <blockquote>
     * <pre>
     *      {@code anyOf(List.of(someClass::condition1, someClass::condition2, someClass::condition3)).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() || someClass.condition2() || someClass.condition3()}
     * </pre></blockquote>
     *
     * @param predicates which will be aggregated
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions.
     * <b><h3>If {@code predicates} is empty
     * then returned expression will contain {@code false}.</h3>
     */
    public static LazyLogicalExpression anyOf(Iterable<NoArgsPredicate> predicates) {
        validatePredicatesChain(predicates);

        LazyLogicalExpression expression = new LazyLogicalExpression(() -> false);
        predicates.forEach(expression::or);

        return expression;
    }

    /**
     * Aggregates all passed predicates with logical OR.
     * <br> Example:
     * <blockquote>
     * <pre>
     *      {@code anyOf(someClass::condition1, someClass::condition2, someClass::condition3).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code someClass.condition1() || someClass.condition2() || someClass.condition3()}
     * </pre></blockquote>
     *
     * @param predicates which will be aggregated
     * @return a LazyLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions.
     * <b><h3>If {@code predicates} is empty
     * then returned expression will contain {@code false}.</h3>
     */
    public static LazyLogicalExpression anyOf(NoArgsPredicate... predicates) {
        return anyOf(Arrays.asList(predicates));
    }

    private static void validatePredicatesChain(Iterable<NoArgsPredicate> predicates) {
        Objects.requireNonNull(predicates);
        predicates.forEach(Objects::requireNonNull);
    }

    /**
     * Combines the current expression with the passed predicate using logical AND.
     *
     * @param predicate that will be merged with the current expression
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression and(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.and(predicate);
        return this;
    }

    /**
     * Combines the current expression with the passed one using logical AND.
     *
     * @param expression that will be merged with the current one
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression and(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.and(expression.finalPredicate);
        return this;
    }

    /**
     * Combines the current expression with the passed predicate using logical OR.
     *
     * @param predicate that will be merged with the current expression
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression or(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.or(predicate);
        return this;
    }

    /**
     * Combines the current expression with the passed one using logical OR.
     *
     * @param expression that will be merged with the current one
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression or(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.or(expression.finalPredicate);
        return this;
    }

    /**
     * Combines the current expression with the passed predicate using XOR.
     *
     * @param predicate that will be merged with the current expression
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression xor(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);

        finalPredicate = finalPredicate.xor(predicate);
        return this;
    }

    /**
     * Combines the current expression with the passed one using XOR.
     *
     * @param expression that will be merged with the current one
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public LazyLogicalExpression xor(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);

        finalPredicate = finalPredicate.xor(expression.finalPredicate);
        return this;
    }

    /**
     * Applies logical NOT to the given predicate.
     *
     * @param predicate to which the operation will be applied
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public static LazyLogicalExpression not(NoArgsPredicate predicate) {
        Objects.requireNonNull(predicate);
        return expr(NoArgsPredicate.not(predicate));
    }

    /**
     * Applies logical NOT to the given expression.
     *
     * @param expression to which the operation will be applied
     * @return a LazyLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public static LazyLogicalExpression not(LazyLogicalExpression expression) {
        Objects.requireNonNull(expression);
        return expr(NoArgsPredicate.not(expression.finalPredicate));
    }

    /**
     * Calculates the result of an expression.
     * @return the logical result of an expression
     */
    public boolean result() {
        return finalPredicate.test();
    }

}
