package ru.nsu.tsydenov.operations;

import ru.nsu.tsydenov.expressions.BinaryExpression;
import ru.nsu.tsydenov.expressions.Expression;

/**
 * Quotient of two expressions.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Div extends BinaryExpression {
    /**
     * Creates a quotient.
     *
     * @param left left operand
     * @param right right operand
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected char operator() {
        return '/';
    }

    @Override
    protected int apply(int left, int right) {
        return left / right;
    }

    @Override
    protected Expression create(Expression left, Expression right) {
        return new Div(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() {
        return simplifyBinary();
    }
}
