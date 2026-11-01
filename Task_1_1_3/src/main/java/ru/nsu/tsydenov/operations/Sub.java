package ru.nsu.tsydenov.operations;

import ru.nsu.tsydenov.expressions.BinaryExpression;
import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;

/**
 * Difference of two expressions.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Sub extends BinaryExpression {
    /**
     * Creates a difference.
     *
     * @param left left operand
     * @param right right operand
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected char operator() {
        return '-';
    }

    @Override
    protected int apply(int left, int right) {
        return left - right;
    }

    @Override
    protected Expression create(Expression left, Expression right) {
        return new Sub(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public Expression simplify() {
        Expression result = simplifyBinary();
        if (result instanceof Sub && ((Sub) result).left.equals(((Sub) result).right)) {
            return new Number(0);
        }
        return result;
    }
}
