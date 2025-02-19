package com.github.evseevda;

import com.github.evseevda.DemoClasses.Human;
import com.github.evseevda.utils.logic.LazyLogicalExpression;
import com.github.evseevda.utils.logic.NoArgsPredicate;

import static com.github.evseevda.utils.logic.LazyLogicalExpression.allOf;

public class LazyLogicalExpressionUsageExamples {

    public static void main(String[] args) {
        NoArgsPredicate TRUE = () -> {
            System.out.println("TRUE");
            return true;
        };
        NoArgsPredicate FALSE = () -> {
            System.out.println("FALSE");
            return false;
        };
//        LazyLogicalExpression expression = expr(TRUE).and(FALSE).or(TRUE);
//        System.out.println(expression.result());
        LazyLogicalExpression expression = allOf(TRUE, TRUE, FALSE).or(TRUE);
//        LazyLogicalExpression expression = anyOf(FALSE, FALSE, TRUE);
        System.out.println(expression.result());
        System.out.println(true && true && false || true);
    }

}
