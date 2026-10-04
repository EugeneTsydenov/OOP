package ru.nsu.tsydenov.operations;

import ru.nsu.tsydenov.expressions.BinaryExpression;
import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;

/**
 * Product of two expressions.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class Mul extends BinaryExpression {
    /**
     * Creates a product.
     *
     * @param left left operand
     * @param right right operand
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected char operator() {
        return '*';
    }

    @Override
    protected int apply(int left, int right) {
        return left * right;
    }

    @Override
    protected Expression create(Expression left, Expression right) {
        return new Mul(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    @Override
    public Expression simplify() {
        Expression result = simplifyBinary();
        if (!(result instanceof Mul)) {
            return result;
        }
        Mul product = (Mul) result;
        if (product.left.equals(new Number(0)) || product.right.equals(new Number(0))) {
            return new Number(0);
        }
        if (product.left.equals(new Number(1))) {
            return product.right;
        }
        if (product.right.equals(new Number(1))) {
            return product.left;
        }
        return product;
    }
}
