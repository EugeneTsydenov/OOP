package ru.nsu.tsydenov.app;

import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;
import ru.nsu.tsydenov.expressions.Variable;
import ru.nsu.tsydenov.operations.Add;
import ru.nsu.tsydenov.operations.Mul;

/**
 * Entry point demonstrating expression operations.
 */
public class Main {

    /**
     * Creates the demonstration entry point.
     */
    public Main() {
    }

    /**
     * Runs a small expression demonstration.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Expression expression = new Add(new Number(3),
                new Mul(new Number(2), new Variable("x")));
        expression.print();
        System.out.println();
        expression.derivative("x").print();
        System.out.println();
        System.out.println(expression.eval("x = 10; y = 13"));
    }
}
