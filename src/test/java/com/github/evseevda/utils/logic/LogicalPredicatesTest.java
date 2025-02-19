package com.github.evseevda.utils.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.evseevda.utils.logic.LogicalPredicates.not;

public class LogicalPredicatesTest {

    @Test
    void whenNativeNotResultIsReturned_ThenLogicalPredicateNotResultIsSame_T1() {
        // arrange
        boolean expression = !true;
        boolean expected = expression;

        // action
        boolean actual = not(true);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenNativeNotResultIsReturned_ThenLogicalPredicateNotResultIsSame_T2() {
        // arrange
        boolean expression = !false;
        boolean expected = expression;

        // action
        boolean actual = not(false);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

}
