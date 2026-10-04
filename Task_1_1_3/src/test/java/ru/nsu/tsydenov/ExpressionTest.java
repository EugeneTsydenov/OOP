package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.app.Main;
import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;
import ru.nsu.tsydenov.expressions.Variable;
import ru.nsu.tsydenov.operations.Add;
import ru.nsu.tsydenov.operations.Div;
import ru.nsu.tsydenov.operations.Mul;
import ru.nsu.tsydenov.operations.Sub;

@DisplayName("Test: Expression class")
class ExpressionTest {

    @Test
    @DisplayName("Test: parsing parenthesized expression")
    void testParse() {
        Expression expression = new Add(new Number(3),
                new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", expression.toString());
        assertEquals(expression, Expression.parse("(3+(2*x))"));
    }

    @Test
    @DisplayName("Test: Parsing expression without redundant parentheses")
    void testParseWithoutParentheses() {
        assertEquals("(3+(2*x))", Expression.parse("3 + 2 * x").toString());
        assertEquals("(a/(b-c))", Expression.parse("a / (b-c)").toString());
    }

    @Test
    @DisplayName("Test: Derivative does not change original expression")
    void testDerivative() {
        Expression expression = new Add(new Number(3),
                new Mul(new Number(2), new Variable("x")));
        assertEquals("(0+((0*x)+(2*1)))", expression.derivative("x").toString());
        assertEquals("(3+(2*x))", expression.toString());
        assertEquals("0", new Variable("longName").derivative("x").toString());
    }

    @Test
    @DisplayName("Test: Evaluation with assignments")
    void testEvaluation() {
        Expression expression = Expression.parse("(3+(2*x))");
        assertEquals(23, expression.eval("x = 10; y = 13"));
        assertEquals(12, Expression.parse("x + longVariable").eval("x=5; longVariable=7"));
    }

    @Test
    @DisplayName("Test: Simplification")
    void testSimplification() {
        Expression expression = new Add(new Mul(new Number(0), new Variable("x")),
                new Sub(new Variable("y"), new Variable("y")));
        assertEquals("0", expression.simplify().toString());
        assertEquals("5", Expression.parse("(2+3)").simplify().toString());
        assertEquals("x", new Mul(new Number(1), new Variable("x")).simplify().toString());
        assertEquals("0", new Mul(new Number(0), new Variable("x")).simplify().toString());
        assertEquals("((0*x)+(y-y))", expression.toString());
    }

    @Test
    @DisplayName("Test: Invalid input and missing values")
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("(x+1"));
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("x+"));
        assertThrows(IllegalArgumentException.class, () -> Expression.parse(null));
        assertThrows(IllegalArgumentException.class, () -> Expression.parse(""));
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("x").eval(""));
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("x").eval("x=bad"));
        assertThrows(IllegalArgumentException.class,
                () -> Expression.parse("x").eval("x=1; broken"));
    }

    @Test
    @DisplayName("Test: Division and aliases")
    void testDivisionAndAliases() {
        Expression expression = Expression.fromString("(x/y)");
        assertEquals(2, expression.eval("x=7; y=3"));
        assertEquals("(((1*y)-(x*0))/(y*y))", expression.derivative("x").toString());
        assertEquals("(x/y)", expression.simplify().toString());
        assertNotEquals(new Div(new Variable("x"), new Variable("z")), expression);
    }

    @Test
    @DisplayName("Test: Main example")
    void testMainExample() {
        new Main();
        Main.main(new String[0]);
    }
}
