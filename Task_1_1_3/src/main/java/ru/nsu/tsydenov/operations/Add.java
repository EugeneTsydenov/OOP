package ru.nsu.tsydenov.operations;

import ru.nsu.tsydenov.expressions.BinaryExpression;
import ru.nsu.tsydenov.expressions.Expression;

/**
 * Sum of two expressions.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Add extends BinaryExpression {
    /**
     * Creates a sum.
     *
     * @param left left operand
     * @param right right operand
     */
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected char operator() {
        return '+';
    }

    @Override
    protected int apply(int left, int right) {
        return left + right;
    }

    @Override
    protected Expression create(Expression left, Expression right) {
        return new Add(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public Expression simplify() {
        return simplifyBinary();
    }
}
