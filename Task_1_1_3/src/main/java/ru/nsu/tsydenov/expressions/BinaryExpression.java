package ru.nsu.tsydenov.expressions;

import java.util.Map;

/**
 * Base class for operations with two operands.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public abstract class BinaryExpression extends Expression {
    /** First operand. */
    protected final Expression left;
    /** Second operand. */
    protected final Expression right;

    /**
     * Creates a binary expression.
     *
     * @param left first operand
     * @param right second operand
     */
    protected BinaryExpression(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Operands cannot be null");
        }
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the operation sign.
     *
     * @return operation sign
     */
    protected abstract char operator();

    /**
     * Applies the operation to two values.
     *
     * @param left first value
     * @param right second value
     * @return operation result
     */
    protected abstract int apply(int left, int right);

    /**
     * Creates the same operation with new operands.
     *
     * @param left first operand
     * @param right second operand
     * @return new expression
     */
    protected abstract Expression create(Expression left, Expression right);

    @Override
    public int eval(Map<String, Integer> variables) {
        return apply(left.eval(variables), right.eval(variables));
    }

    @Override
    public String toString() {
        return "(" + left + operator() + right + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        BinaryExpression expression = (BinaryExpression) object;
        return left.equals(expression.left) && right.equals(expression.right);
    }

    @Override
    public int hashCode() {
        return 31 * (31 * getClass().hashCode() + left.hashCode()) + right.hashCode();
    }

    /**
     * Simplifies both operands.
     *
     * @return simpler expression
     */
    protected Expression simplifyBinary() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();
        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(apply(((Number) simplifiedLeft).eval(Map.of()),
                    ((Number) simplifiedRight).eval(Map.of())));
        }
        return create(simplifiedLeft, simplifiedRight);
    }
}
