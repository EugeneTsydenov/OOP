package ru.nsu.tsydenov.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test: Variable class")
class VariableTest {

    @Test
    @DisplayName("Test: Variable prints its name")
    void testVariablePrint() {
        Variable variable = new Variable("value");
        assertEquals("value", variable.toString());
    }

    @Test
    @DisplayName("Test: Variable evaluates from a map")
    void testVariableEvaluation() {
        Variable variable = new Variable("value");
        Map<String, Integer> values = Map.of("value", 12);
        assertEquals(12, variable.eval(values));
    }

    @Test
    @DisplayName("Test: Variable derivative is one for the same name")
    void testVariableDerivativeForSameName() {
        Variable variable = new Variable("value");
        assertEquals("1", variable.derivative("value").toString());
    }

    @Test
    @DisplayName("Test: Variable derivative is zero for another name")
    void testVariableDerivativeForAnotherName() {
        Variable variable = new Variable("value");
        assertEquals("0", variable.derivative("other").toString());
    }

    @Test
    @DisplayName("Test: Variable simplifies to itself")
    void testVariableSimplification() {
        Variable variable = new Variable("value");
        assertEquals(variable, variable.simplify());
    }

    @Test
    @DisplayName("Test: Variables compare by name")
    void testVariableEquality() {
        Variable variable = new Variable("value");
        assertEquals(variable.hashCode(), new Variable("value").hashCode());
    }

    @Test
    @DisplayName("Test: Variable rejects missing values")
    void testVariableWithoutValue() {
        Variable variable = new Variable("value");
        assertThrows(IllegalArgumentException.class, () -> variable.eval(new HashMap<>()));
    }

    @Test
    @DisplayName("Test: Variable rejects empty names")
    void testEmptyVariableName() {
        assertThrows(IllegalArgumentException.class, () -> new Variable(""));
    }

    @Test
    @DisplayName("Test: Variable rejects null names")
    void testNullVariableName() {
        assertThrows(IllegalArgumentException.class, () -> new Variable(null));
    }
}
