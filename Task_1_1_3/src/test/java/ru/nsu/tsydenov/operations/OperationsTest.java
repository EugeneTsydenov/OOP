package ru.nsu.tsydenov.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;
import ru.nsu.tsydenov.expressions.Variable;

@DisplayName("Test: Arithmetic operations")
class OperationsTest {

    @Test
    @DisplayName("Test: Add calculates a value")
    void testAddEvaluation() {
        Expression left = new Number(8);
        Expression right = new Number(2);
        assertEquals("(8+2)", new Add(left, right).toString());
        assertEquals(10, new Add(left, right).eval(""));
    }

    @Test
    @DisplayName("Test: Sub calculates a value")
    void testSubEvaluation() {
        Expression left = new Number(8);
        Expression right = new Number(2);
        assertEquals("(8-2)", new Sub(left, right).toString());
        assertEquals(6, new Sub(left, right).eval(""));
    }

    @Test
    @DisplayName("Test: Mul calculates a value")
    void testMulEvaluation() {
        Expression left = new Number(8);
        Expression right = new Number(2);
        assertEquals("(8*2)", new Mul(left, right).toString());
        assertEquals(16, new Mul(left, right).eval(""));
    }

    @Test
    @DisplayName("Test: Div calculates a value")
    void testDivEvaluation() {
        Expression left = new Number(8);
        Expression right = new Number(2);
        assertEquals("(8/2)", new Div(left, right).toString());
        assertEquals(4, new Div(left, right).eval(""));
    }

    @Test
    @DisplayName("Test: Add rejects null operands")
    void testAddNullOperand() {
        assertThrows(IllegalArgumentException.class, () -> new Add(null, new Number(1)));
    }

    @Test
    @DisplayName("Test: Sub rejects null operands")
    void testSubNullOperand() {
        assertThrows(IllegalArgumentException.class, () -> new Sub(new Number(1), null));
    }

    @Test
    @DisplayName("Test: Div rejects division by zero")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class,
                () -> new Div(new Number(1), new Number(0)).eval(""));
    }

    @Test
    @DisplayName("Test: Operations simplify expressions with variables")
    void testNonConstantSimplification() {
        Expression x = new Variable("x");
        assertEquals("(x+1)", new Add(x, new Number(1)).simplify().toString());
        assertEquals("(x-1)", new Sub(x, new Number(1)).simplify().toString());
        assertEquals("(x*2)", new Mul(x, new Number(2)).simplify().toString());
        assertEquals("(x/2)", new Div(x, new Number(2)).simplify().toString());
    }

    @Test
    @DisplayName("Test: Operations create correct derivatives")
    void testOperationDerivatives() {
        Expression x = new Variable("x");
        Expression y = new Variable("y");
        assertEquals("(1+0)", new Add(x, y).derivative("x").toString());
        assertEquals("(1-0)", new Sub(x, y).derivative("x").toString());
        assertEquals("((1*y)+(x*0))", new Mul(x, y).derivative("x").toString());
        assertEquals("(((1*y)-(x*0))/(y*y))", new Div(x, y).derivative("x").toString());
    }

    @Test
    @DisplayName("Test: Multiplication simplifies zero and one")
    void testMultiplicationSimplifiesZeroAndOne() {
        Expression x = new Variable("x");
        assertEquals("0", new Mul(x, new Number(0)).simplify().toString());
        assertEquals("x", new Mul(x, new Number(1)).simplify().toString());
        assertEquals("x", new Mul(new Number(1), x).simplify().toString());
    }

    @Test
    @DisplayName("Test: Subtraction simplifies equal expressions")
    void testSubtractionSimplifiesEqualExpressions() {
        Expression x = new Variable("x");
        assertEquals("0", new Sub(x, x).simplify().toString());
        assertEquals("(x-y)", new Sub(x, new Variable("y")).simplify().toString());
    }
}
