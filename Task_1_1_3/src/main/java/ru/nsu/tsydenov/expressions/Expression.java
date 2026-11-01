package ru.nsu.tsydenov.expressions;

import java.util.Map;
import ru.nsu.tsydenov.parsing.Assignments;
import ru.nsu.tsydenov.parsing.ExpressionParser;

/**
 * Base class for all expressions.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public abstract class Expression {

    /**
     * Creates an expression.
     */
    protected Expression() {
    }

    /**
     * Reads an expression from text.
     *
     * @param text expression text
     * @return expression object
     * @throws IllegalArgumentException if the text is invalid
     */
    public static Expression parse(String text) {
        return new ExpressionParser(text).parse();
    }

    /**
     * Reads an expression from text.
     *
     * @param text expression text
     * @return expression object
     */
    public static Expression fromString(String text) {
        return parse(text);
    }

    /** Prints the expression. */
    public void print() {
        System.out.print(this + "\n");
    }

    /**
     * Finds the derivative by a variable.
     *
     * @param variable variable name
     * @return new derivative
     */
    public abstract Expression derivative(String variable);

    /**
     * Calculates the value using assignments.
     *
     * @param assignments assignments such as {@code x = 10; y = 13}
     * @return expression value
     */
    public int eval(String assignments) {
        return eval(Assignments.parse(assignments));
    }

    /**
     * Calculates the value using a map of variables.
     *
     * @param variables variable values
     * @return expression value
     */
    public abstract int eval(Map<String, Integer> variables);

    /**
     * Creates a simpler expression.
     *
     * @return new simplified expression
     */
    public abstract Expression simplify();

}
