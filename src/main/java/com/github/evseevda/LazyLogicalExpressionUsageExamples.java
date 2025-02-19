package com.github.evseevda;

import com.github.evseevda.utils.logic.LazyLogicalExpression;

import static com.github.evseevda.DemoClasses.*;
import static com.github.evseevda.DemoClasses.Bank.*;
import static com.github.evseevda.DemoClasses.Calendar.isFavouriteMonth;
import static com.github.evseevda.DemoClasses.Calendar.isHoliday;
import static com.github.evseevda.DemoClasses.Human.isGoodMood;
import static com.github.evseevda.DemoClasses.Weather.*;
import static com.github.evseevda.utils.logic.LazyLogicalExpression.allOf;
import static com.github.evseevda.utils.logic.LazyLogicalExpression.expr;
import static com.github.evseevda.utils.logic.NoArgsPredicate.not;

/**
 * The main goal of this class is to show how to use class {@code LazyLogicalExpression}.
 * @since 0.9.0
 * @see com.github.evseevda.utils.logic.LazyLogicalExpression
 * @author EvseevDA
 */
public class LazyLogicalExpressionUsageExamples {

    /**
     * Shows how {@code LazyLogicalExpression} class can be used
     * @see com.github.evseevda.utils.logic.LazyLogicalExpression
     */
    public static void examples() {
        // Example 1
        if (userIsNotLocked() && balance() >= amountNeeded()) {
            System.out.println("Purchase is approved");
        }

        LazyLogicalExpression expression1 =
                expr(Bank::userIsNotLocked).and(() -> balance() >= amountNeeded()); // expression defined here

        if (expression1.result()) { // and calculated here
            System.out.println("Purchase is approved");
        }


        // Example 2
        if (isWinter() && isSunny() || !isCold()) {
            System.out.println("Time to walk");
        }

        LazyLogicalExpression expression2 =
                expr(Weather::isWinter).and(Weather::isSunny).or(not(Weather::isCold)); // expression defined here

        if (expression2.result()) { // and calculated here
            System.out.println("Time to walk");
        }


        // Example 3
        if ((isSunny() && !isWinter()) || (isFavouriteMonth() && !isCold()) || isGoodMood()) {
            System.out.println("Have cause to walk too");
        }

        LazyLogicalExpression expression3 =
                expr(Weather::isSunny).and(not(Weather::isWinter))
                        .or(expr(Calendar::isFavouriteMonth).and(not(Weather::isCold)))
                        .or(Human::isGoodMood); // expression defined here

        if (expression3.result()) { // and calculated here
            System.out.println("Have cause to walk too");
        }


        // Example 4
        if (
                isSunny() && !isWinter() && !isCold() && isFavouriteMonth() && isHoliday()
                        && isGoodMood() && balance() >= amountNeeded()
        ) {
            System.out.println("Can walk to shop");
        }

        LazyLogicalExpression expression4 =
                allOf(Weather::isSunny, not(Weather::isWinter), not(Weather::isCold),
                        Calendar::isFavouriteMonth, Calendar::isHoliday, Human::isGoodMood,
                        () -> balance() >= amountNeeded()); // expression defined here

        if (expression4.result()) { // and calculated here
            System.out.println("Can walk to shop");
        }

    }

}
