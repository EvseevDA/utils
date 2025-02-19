package com.github.evseevda.utils.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.evseevda.utils.logic.LazyLogicalExpression.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LazyLogicalExpressionTest {

    private static final NoArgsPredicate TRUE = () -> true;
    private static final NoArgsPredicate FALSE = () -> false;

    private NoArgsPredicate mockedPredicate = Mockito.mock(NoArgsPredicate.class);

    @Test
    void whenSimpleNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = true;
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true && false);
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).and(FALSE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = (true && true && true && true);
        boolean expected = expression;

        // action
        boolean actual = allOf(TRUE, TRUE, TRUE, TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = (true && true && true && false);
        boolean expected = expression;

        // action
        boolean actual = allOf(TRUE, TRUE, TRUE, FALSE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true || false);
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).or(TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = (false || false || false || false);
        boolean expected = expression;

        // action
        boolean actual = anyOf(FALSE, FALSE, FALSE, FALSE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = (false || false || false || true);
        boolean expected = expression;

        // action
        boolean actual = anyOf(FALSE, FALSE, FALSE, TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithXorResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true ^ false);
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).xor(FALSE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithXorResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = true ^ (true && false);
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).xor(expr(TRUE).and(FALSE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = (true && false || true);
        boolean expected = expression;

        // action
        boolean actual = expr(TRUE).and(FALSE).or(TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleNativeLogicalExpressionWithNotResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !true;
        boolean expected = expression;

        // action
        boolean actual = not(TRUE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleNativeLogicalExpressionWithNotAndAndResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !(true && false);
        boolean expected = expression;

        // action
        boolean actual = not(expr(TRUE).and(FALSE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleComposedNativeLogicalExpressionWithNotAndAndResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !(true && false) && false;
        boolean expected = expression;

        // action
        boolean actual = not(expr(TRUE).and(FALSE)).and(FALSE).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = ((true && false) || (true && true));
        boolean expected = expression;

        // action
        boolean actual = (expr(TRUE).and(FALSE)).or(expr(TRUE).and(TRUE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = ((true && false) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(TRUE).and(FALSE)).or(expr(TRUE).and(FALSE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = ((true && true) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(TRUE).and(TRUE)).or(expr(TRUE).and(FALSE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T4() {
        // arrange
        boolean expression = ((true && false) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(TRUE).and(FALSE)).or(expr(TRUE).and(FALSE)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComplexComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (
                (true && false)
                        || (true && false)
                        && (
                        (true && true)
                                || (true ^ false)
                )
        );
        boolean expected = expression;

        // action
        boolean actual =
                (expr(TRUE).and(FALSE))
                        .or(expr(TRUE).and(FALSE))
                        .and(
                                expr(expr(TRUE).and(TRUE))
                                        .or(expr(TRUE).xor(FALSE)))
                        .result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComplexComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = (
                (true && false)
                        || (true && false)
                        && (
                        (true && false)
                                || (true ^ false)
                )
        );
        boolean expected = expression;

        // action
        boolean actual =
                (expr(TRUE).and(FALSE))
                        .or(expr(TRUE).and(FALSE))
                        .and(
                                expr(
                                        expr(TRUE).and(FALSE)
                                )
                                        .or(expr(TRUE).xor(FALSE))
                        )
                        .result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComplexComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = (
                (true && false)
                        || (true && true)
                        && (
                        (true && false)
                                || (true ^ false)
                )
        );
        boolean expected = expression;

        // action
        boolean actual =
                (expr(TRUE).and(TRUE))
                        .or(expr(TRUE).and(TRUE))
                        .and(
                                expr(
                                        expr(TRUE).and(FALSE)
                                )
                                        .or(expr(TRUE).xor(FALSE))
                        )
                        .result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComplexComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T4() {
        // arrange
        boolean expression = (
                (true && false)
                        || (true && true)
                        && !(
                        (true && false)
                                || (true ^ false)
                )
        );
        boolean expected = expression;

        // action
        boolean actual =
                (expr(TRUE).and(FALSE))
                        .or(expr(TRUE).and(TRUE))
                        .and(
                                not(
                                        expr(
                                                expr(TRUE).and(FALSE)
                                        ).or(expr(TRUE).xor(FALSE))
                                )
                        )
                        .result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComplexComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T5() {
        // arrange
        boolean expression = !(
                (true && false)
                        || (true && true)
                        && !(
                        (true && false)
                                || (true ^ false)
                )
        );
        boolean expected = expression;

        // action
        boolean actual =
                not(
                        (expr(TRUE).and(FALSE))
                                .or(expr(TRUE).and(TRUE))
                                .and(
                                        not(
                                                expr(
                                                        expr(TRUE).and(FALSE)
                                                ).or(expr(TRUE).xor(FALSE))
                                        )
                                )
                ).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenMethodResultIsNotCalled_ThenExpressionsIsNotCalculating_T1() {
        // arrange && action
        when(mockedPredicate.and(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.or(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.xor(any())).thenReturn(mockedPredicate);
        expr(mockedPredicate).and(mockedPredicate).or(mockedPredicate).xor(mockedPredicate);

        // assertion
        verify(mockedPredicate, times(0)).test();
    }

    @Test
    void whenMethodResultIsNotCalled_ThenExpressionsIsNotCalculating_T2() {
        // arrange && action
        when(mockedPredicate.and(any())).thenReturn(mockedPredicate);
        allOf(mockedPredicate, mockedPredicate, mockedPredicate, mockedPredicate);

        // assertion
        verify(mockedPredicate, times(0)).test();
    }

    @Test
    void whenMethodResultIsNotCalled_ThenExpressionsIsNotCalculating_T3() {
        // arrange && action
        when(mockedPredicate.or(any())).thenReturn(mockedPredicate);
        anyOf(mockedPredicate, mockedPredicate, mockedPredicate, mockedPredicate);

        // assertion
        verify(mockedPredicate, times(0)).test();
    }

    @Test
    void whenMethodResultIsNotCalled_ThenExpressionsIsNotCalculating_T4() {
        // arrange && action
        when(mockedPredicate.and(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.or(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.xor(any())).thenReturn(mockedPredicate);
        not(expr(mockedPredicate).and(mockedPredicate).or(mockedPredicate).xor(mockedPredicate));

        // assertion
        verify(mockedPredicate, times(0)).test();
    }

    @Test
    void whenMethodResultIsNotCalled_ThenExpressionsIsNotCalculating_T5() {
        // arrange && action
        when(mockedPredicate.and(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.or(any())).thenReturn(mockedPredicate);
        when(mockedPredicate.xor(any())).thenReturn(mockedPredicate);
        expr(not(mockedPredicate)).and(not(mockedPredicate)).or(not(mockedPredicate)).xor(not(mockedPredicate));

        // assertion
        verify(mockedPredicate, times(0)).test();
    }

}