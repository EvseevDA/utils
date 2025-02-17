package com.github.evseevda.utils.logic;

import java.util.Objects;

/**
 * The main goal of this class is to replace native Java logic operators {@code (&&, ||, ^, !)}
 * with English equivalents.
 * It is assumed that in this case the code will acquire a more human-friendly appearance,
 * which will increase its readability.
 * @since 0.9.0
 * @see com.github.evseevda.UsageExamples
 * @author EvseevDA
 */
public class EagerLogicalExpression {

    private boolean expressionResult;

    private EagerLogicalExpression(boolean root) {
        this.expressionResult = root;
    }

    private EagerLogicalExpression(EagerLogicalExpression root) {
        Objects.requireNonNull(root);
        this.expressionResult = root.expressionResult;
    }

    /**
     * Creates a new logical expression.
     * <br> Calling this method is equivalent to natively putting
     * parentheses around the expression.
     * <br> If expr is called once in a method call chain of this class,
     * the logical operators are called in direct order and in turn.
     * <blockquote>
     *     <pre>
     *      {@code expr(condition1()).and(condition2()).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code condition1() && condition2()}
     * </pre></blockquote>
     * <br> If expr occurs twice, the results of the expressions written before the second expr
     * will be calculated first, then the results of the expressions after the second expr
     * will be calculated, and then the results of the expressions will be combined.
     * When expr occurs more than twice, the situation is similar.
     * <blockquote>
     *     <pre>
     *      {@code expr(condition1()).and(condition2()).or(expr(condition3()).or(condition4()))}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code (condition1() && condition2()) || (condition3() || condition4())}
     * </pre></blockquote>
     * @param root the first expression in a chain of expressions
     * @return an EagerLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static EagerLogicalExpression expr(boolean root) {
        return new EagerLogicalExpression(root);
    }

    /**
     * In general, it is similar to expr with a boolean parameter, but now the main expression is another expression.
     * <br> This method is needed to create nested expressions.
     * <br> Example:
     * <blockquote>
     *     <pre>
     *      {@code expr(condition1()).and(expr(expr(condition2()).or(condition3()).and(not(condition4())))).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code condition1() && ((condition2() || condition3()) && !condition4())}
     * </pre></blockquote>
     * @param root the first expression in a chain of expressions
     * @return an EagerLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static EagerLogicalExpression expr(EagerLogicalExpression root) {
        Objects.requireNonNull(root);
        return new EagerLogicalExpression(root);
    }

    /**
     * Aggregates all passed expressions with logical AND.
     * <br> Example:
     * <blockquote>
     *     <pre>
     *      {@code allOf(condition1(), condition2(), condition3()).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code condition1() && condition2() && condition3()}
     * </pre></blockquote>
     * @param expressions which will be aggregated
     * @return an EagerLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static EagerLogicalExpression allOf(boolean... expressions) {
        Objects.requireNonNull(expressions);

        EagerLogicalExpression logicalExpression = new EagerLogicalExpression(true);
        for (boolean expression : expressions) {
            logicalExpression.expressionResult &= expression;
        }
        return logicalExpression;
    }

    /**
     * Aggregates all passed expressions with logical OR.
     * <br> Example:
     * <blockquote>
     *     <pre>
     *      {@code anyOf(condition1(), condition2(), condition3()).result()}
     *     </pre>
     * </blockquote>
     * is equals to
     * <blockquote><pre>
     *      {@code condition1() || condition2() || condition3()}
     * </pre></blockquote>
     * @param expressions which will be aggregated
     * @return an EagerLogicalExpression object that serves as the
     * basis for constructing the subsequent chain of expressions
     */
    public static EagerLogicalExpression anyOf(boolean... expressions) {
        Objects.requireNonNull(expressions);

        EagerLogicalExpression logicalExpression = new EagerLogicalExpression(false);
        for (boolean expression : expressions) {
            logicalExpression.expressionResult |= expression;
        }
        return logicalExpression;
    }

    /**
     * Combines the current expression with the passed one using logical AND.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression and(EagerLogicalExpression expression) {
        Objects.requireNonNull(expression);

        expressionResult &= expression.result();
        return this;
    }

    /**
     * Combines the current expression with the passed one using logical AND.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression and(boolean expression) {
        expressionResult &= expression;
        return this;
    }

    /**
     * Combines the current expression with the passed one using logical OR.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression or(EagerLogicalExpression expression) {
        Objects.requireNonNull(expression);

        expressionResult |= expression.result();
        return this;
    }

    /**
     * Combines the current expression with the passed one using logical OR.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression or(boolean expression) {
        expressionResult |= expression;
        return this;
    }

    /**
     * Combines the current expression with the passed one using XOR.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression xor(EagerLogicalExpression expression) {
        Objects.requireNonNull(expression);

        expressionResult ^= expression.result();
        return this;
    }

    /**
     * Combines the current expression with the passed one using XOR.
     * @param expression that will be merged with the current one
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public EagerLogicalExpression xor(boolean expression) {
        expressionResult ^= expression;
        return this;
    }

    /**
     * Applies logical NOT to the given expression.
     * @param expression to which the operation will be applied
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public static EagerLogicalExpression not(EagerLogicalExpression expression) {
        Objects.requireNonNull(expression);

        return expr(!expression.result());
    }

    /**
     * Applies logical NOT to the given expression.
     * @param expression to which the operation will be applied
     * @return an EagerLogicalExpression object that can be used to further construct a chain of expressions.
     */
    public static EagerLogicalExpression not(boolean expression) {
        return expr(!expression);
    }

    /**
     * @return the result of a logical expression
     */
    public boolean result() {
        return expressionResult;
    }

}
