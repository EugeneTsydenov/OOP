package ru.nsu.tsydenov.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.expressions.Expression;

@DisplayName("Test: Expression parser")
class ParserTest {

    @Test
    @DisplayName("Test: Parser reads negative numbers")
    void testNegativeNumber() {
        assertEquals("-12", Expression.parse("-12").toString());
    }

    @Test
    @DisplayName("Test: Parser reads variable names")
    void testVariableName() {
        assertEquals("(_x1+2)", Expression.parse("_x1 + 2").toString());
    }

    @Test
    @DisplayName("Test: Parser respects operation priority")
    void testOperationPriority() {
        assertEquals("(1+(2*(3-4)))", Expression.parse("1 + 2 * (3 - 4)").toString());
    }

    @Test
    @DisplayName("Test: Parser rejects unexpected characters")
    void testUnexpectedCharacter() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("1 + $"));
    }

    @Test
    @DisplayName("Test: Parser rejects missing closing parenthesis")
    void testMissingClosingParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("(1+2"));
    }

    @Test
    @DisplayName("Test: Parser rejects extra closing parenthesis")
    void testExtraClosingParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("1)"));
    }

    @Test
    @DisplayName("Test: Parser rejects too large numbers")
    void testTooLargeNumber() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("2147483648"));
    }

    @Test
    @DisplayName("Test: Parser rejects incomplete expressions")
    void testIncompleteExpression() {
        assertThrows(IllegalArgumentException.class, () -> Expression.parse("1 +"));
    }
}
