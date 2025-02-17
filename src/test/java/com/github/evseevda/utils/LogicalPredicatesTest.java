package com.github.evseevda.utils;

import com.github.evseevda.utils.logic.LogicalPredicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.evseevda.utils.logic.LogicalPredicates.*;

public class LogicalPredicatesTest {

    private static boolean TRUE = (1 == 1);
    private static boolean FALSE = (1 == 2);

    @Test
    void whenNativeNotResultIsReturned_ThenLogicalPredicateNotResultIsSame_T1() {
        // arrange
        boolean expression = !TRUE;
        boolean expected = expression;

        // action
        boolean actual = not(TRUE);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenNativeNotResultIsReturned_ThenLogicalPredicateNotResultIsSame_T2() {
        // arrange
        boolean expression = !FALSE;
        boolean expected = expression;

        // action
        boolean actual = not(FALSE);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

}
