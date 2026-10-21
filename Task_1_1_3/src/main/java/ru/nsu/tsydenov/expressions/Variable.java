package ru.nsu.tsydenov.expressions;

import java.util.Map;

/**
 * A named variable expression.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Creates a variable.
     *
     * @param name variable name
     */
    public Variable(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("No value for variable: " + name);
        }
        return variables.get(name);
    }

    @Override
    public Expression simplify() {
        return new Variable(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Variable && name.equals(((Variable) object).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
