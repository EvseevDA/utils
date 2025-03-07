package com.github.evseevda;

import static com.github.evseevda.DemoClasses.Bank.*;
import static com.github.evseevda.DemoClasses.Calendar.isFavouriteMonth;
import static com.github.evseevda.DemoClasses.Calendar.isHoliday;
import static com.github.evseevda.DemoClasses.Human.isGoodMood;
import static com.github.evseevda.DemoClasses.Weather.*;
import static com.github.evseevda.utils.logic.EagerLogicalExpression.allOf;
import static com.github.evseevda.utils.logic.EagerLogicalExpression.expr;
import static com.github.evseevda.utils.logic.LogicalPredicates.not;

/**
 * The main goal of this class is to show how to use class {@code EagerLogicalExpression}.
 * @since 0.9.0
 * @see com.github.evseevda.utils.logic.EagerLogicalExpression
 * @see com.github.evseevda.utils.logic.LogicalPredicates
 * @author EvseevDA
 */
public class EagerLogicalExpressionUsageExamples {

    /**
     * Shows how {@code EagerLogicalExpression} class can be used
     * @see com.github.evseevda.utils.logic.EagerLogicalExpression
     */
    public static void examples() {
        // Example 1
        if (userIsNotLocked() && balance() >= amountNeeded()) {
            System.out.println("Purchase is approved");
        }

        if (expr(userIsNotLocked()).and(balance() >= amountNeeded()).result()) {
            System.out.println("Purchase is approved");
        }

        // Example 2
        if (isWinter() && isSunny() || !isCold()) {
            System.out.println("Time to walk");
        }

        if (expr(isWinter()).and(isSunny()).or(not(isCold())).result()) {
            System.out.println("Time to walk");
        }

        // Example 3
        if ((isSunny() && !isWinter()) || (isFavouriteMonth() && !isCold()) || isGoodMood()) {
            System.out.println("Have cause to walk too");
        }

        if (
                expr(isSunny()).and(not(isWinter()))
                        .or(expr(isFavouriteMonth()).and(not(isCold())))
                        .or(isGoodMood()).result()
        ) {
            System.out.println("Have cause to walk too");
        }

        // Example 4
        if (
                isSunny() && !isWinter() && !isCold() && isFavouriteMonth() && isHoliday()
                        && isGoodMood() && balance() >= amountNeeded()
        ) {
            System.out.println("Can walk to shop");
        }

        if (
                allOf(isSunny(), not(isWinter()), not(isCold()), isFavouriteMonth(), isHoliday(),
                        isGoodMood(), balance() >= amountNeeded()).result()
        ) {
            System.out.println("Can walk to shop");
        }

    }
}
