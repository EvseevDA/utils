package com.github.evseevda.utils.logic;

/**
 * The main purpose of the class is to represent logical operations as methods.
 * <br> The advantage of this approach is that the method is named and this contributes
 * to the increase in readability of the code.
 * @since 0.9.0
 * @see EagerLogicalExpression
 * @author EvseevDA
 */
public class LogicalPredicates {

    /**
     * Applies logical NOT to the given expression.
     * @param expression to which the operation will be applied
     * @return the inverted expression
     */
    public static boolean not(boolean expression) {
        return !expression;
    }

}
