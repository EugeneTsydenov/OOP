package ru.nsu.tsydenov.expressions;

import java.util.Map;

/**
 * An integer constant expression.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Number extends Expression {
    private final int value;

    /**
     * Creates a constant.
     *
     * @param value constant value
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression simplify() {
        return new Number(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Number && value == ((Number) object).value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
