package com.github.evseevda;

import com.github.evseevda.utils.logic.LazyLogicalExpression;
import com.github.evseevda.utils.logic.NoArgsPredicate;

import static com.github.evseevda.utils.logic.LazyLogicalExpression.expr;

public class LazyLogicalExpressionUsageExamples {

    public static void main(String[] args) {
        NoArgsPredicate TRUE = () -> true;
        NoArgsPredicate FALSE = () -> false;
        LazyLogicalExpression expression = expr(TRUE).and(TRUE).or(expr(FALSE).and(TRUE));
        System.out.println(expression.result());
    }

}
