package ru.nsu.tsydenov.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test: Number class")
class NumberTest {

    @Test
    @DisplayName("Test: Number prints its value")
    void testNumberPrint() {
        Number number = new Number(42);
        assertEquals("42", number.toString());
    }

    @Test
    @DisplayName("Test: Number evaluates to its value")
    void testNumberEvaluation() {
        Number number = new Number(42);
        assertEquals(42, number.eval(new HashMap<>()));
    }

    @Test
    @DisplayName("Test: Number derivative is zero")
    void testNumberDerivative() {
        Number number = new Number(42);
        assertEquals("0", number.derivative("x").toString());
    }

    @Test
    @DisplayName("Test: Number simplifies to itself")
    void testNumberSimplification() {
        Number number = new Number(42);
        assertEquals(number, number.simplify());
    }

    @Test
    @DisplayName("Test: Numbers compare by value")
    void testNumberEquality() {
        Number number = new Number(42);
        assertEquals(number.hashCode(), new Number(42).hashCode());
        assertNotEquals(new Number(7), number);
        assertNotEquals("42", number);
    }
}
