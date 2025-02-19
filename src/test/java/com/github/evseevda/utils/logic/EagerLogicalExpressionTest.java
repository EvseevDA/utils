package com.github.evseevda.utils.logic;

import org.junit.jupiter.api.Test;

import static com.github.evseevda.utils.logic.EagerLogicalExpression.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EagerLogicalExpressionTest {

    @Test
    void whenSimpleNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = true;
        boolean expected = expression;

        // action
        boolean actual = expr(true).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true && false);
        boolean expected = expression;

        // action
        boolean actual = expr(true).and(false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = (true && true && true && true);
        boolean expected = expression;

        // action
        boolean actual = allOf(true, true, true, true).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithAndResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = (true && true && true && false);
        boolean expected = expression;

        // action
        boolean actual = allOf(true, true, true, false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true || false);
        boolean expected = expression;

        // action
        boolean actual = expr(true).or(false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = (false || false || false || false);
        boolean expected = expression;

        // action
        boolean actual = anyOf(false, false, false, false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithOrResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = (false || false || false || true);
        boolean expected = expression;

        // action
        boolean actual = anyOf(false, false, false, true).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithXorResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = (true ^ false);
        boolean expected = expression;

        // action
        boolean actual = expr(true).xor(false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenNativeLogicalExpressionWithXorResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = true ^ (true && false);
        boolean expected = expression;

        // action
        boolean actual = expr(true).xor(expr(true).and(false)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = (true && false || true);
        boolean expected = expression;

        // action
        boolean actual = expr(true).and(false).or(true).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleNativeLogicalExpressionWithNotResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !true;
        boolean expected = expression;

        // action
        boolean actual = not(true).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleNativeLogicalExpressionWithNotAndAndResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !(true && false);
        boolean expected = expression;

        // action
        boolean actual = not(expr(true).and(false)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenSimpleComposedNativeLogicalExpressionWithNotAndAndResultIsReturned_ThenExpressionResultIsSame() {
        // arrange
        boolean expression = !(true && false) && false;
        boolean expected = expression;

        // action
        boolean actual = not(expr(true).and(false)).and(false).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T1() {
        // arrange
        boolean expression = ((true && false) || (true && true));
        boolean expected = expression;

        // action
        boolean actual = (expr(true).and(false)).or(expr(true).and(true)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T2() {
        // arrange
        boolean expression = ((true && false) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(true).and(false)).or(expr(true).and(false)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T3() {
        // arrange
        boolean expression = ((true && true) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(true).and(true)).or(expr(true).and(false)).result();

        // assertion
        assertEquals(expected, actual);
    }

    @Test
    void whenComposedNativeLogicalExpressionResultIsReturned_ThenExpressionResultIsSame_T4() {
        // arrange
        boolean expression = ((true && false) || (true && false));
        boolean expected = expression;

        // action
        boolean actual = (expr(true).and(false)).or(expr(true).and(false)).result();

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
                (expr(true).and(false))
                        .or(expr(true).and(false))
                        .and(
                                expr(expr(true).and(true))
                                        .or(expr(true).xor(false)))
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
                (expr(true).and(false))
                        .or(expr(true).and(false))
                        .and(
                                expr(
                                        expr(true).and(false)
                                )
                                        .or(expr(true).xor(false))
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
                (expr(true).and(false))
                        .or(expr(true).and(true))
                        .and(
                                expr(
                                        expr(true).and(false)
                                )
                                        .or(expr(true).xor(false))
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
                (expr(true).and(false))
                        .or(expr(true).and(true))
                        .and(
                                not(
                                        expr(
                                                expr(true).and(false)
                                        ).or(expr(true).xor(false))
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
                        (expr(true).and(false))
                                .or(expr(true).and(true))
                                .and(
                                        not(
                                                expr(
                                                        expr(true).and(false)
                                                ).or(expr(true).xor(false))
                                        )
                                )
                ).result();

        // assertion
        assertEquals(expected, actual);
    }

}
