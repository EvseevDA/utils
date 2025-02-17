package com.github.evseevda;

import static com.github.evseevda.UsageExamples.Bank.*;
import static com.github.evseevda.UsageExamples.Calendar.*;
import static com.github.evseevda.UsageExamples.Human.isGoodMood;
import static com.github.evseevda.UsageExamples.Weather.*;
import static com.github.evseevda.utils.logic.EagerLogicalExpression.allOf;
import static com.github.evseevda.utils.logic.EagerLogicalExpression.expr;
import static com.github.evseevda.utils.logic.LogicalPredicates.not;

/**
 * The main goal of this class is to show how to use the features presented in this project.
 * @since 0.9.0
 * @see com.github.evseevda.utils.logic.EagerLogicalExpression
 * @see com.github.evseevda.utils.logic.LogicalPredicates
 * @author EvseevDA
 */
public class UsageExamples {

    /**
     * Shows how {@code EagerLogicalExpression} class can be used
     * @see com.github.evseevda.utils.logic.EagerLogicalExpression
     */
    public static void eagerLogicalExpressionExamples() {
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

    static class Human {
        public static boolean isGoodMood() {
            return true;
        }
    }

    static class Bank {

        public static boolean userIsNotLocked() {
            return true;
        }

        public static int balance() {
            return 100;
        }

        public static int amountNeeded() {
            return 99;
        }

    }

    static class Calendar {

        public static boolean isHoliday() {
            return false;
        }

        public static boolean isFebruary() {
            return true;
        }

        public static boolean isFavouriteMonth() {
            return true;
        }

    }

    static class Weather {

        public static boolean isWinter() {
            return true;
        }

        public static boolean isSunny() {
            return false;
        }

        public static boolean isCold() {
            return true;
        }

    }
}
