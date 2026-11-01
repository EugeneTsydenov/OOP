package ru.nsu.tsydenov.parsing;

import ru.nsu.tsydenov.expressions.Expression;
import ru.nsu.tsydenov.expressions.Number;
import ru.nsu.tsydenov.expressions.Variable;
import ru.nsu.tsydenov.operations.Add;
import ru.nsu.tsydenov.operations.Div;
import ru.nsu.tsydenov.operations.Mul;
import ru.nsu.tsydenov.operations.Sub;

/**
 * Reads arithmetic expressions from text.
 */
public final class ExpressionParser {
    private final String text;
    private int position;

    /**
     * Creates a parser.
     *
     * @param text expression text
     */
    public ExpressionParser(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        this.text = text;
    }

    /**
     * Parses the complete expression.
     *
     * @return parsed expression
     */
    public Expression parse() {
        Expression result = parseExpression();
        skipSpaces();
        if (position != text.length()) {
            throw error("Unexpected character");
        }
        return result;
    }

    private Expression parseExpression() {
        Expression result = parseTerm();
        while (true) {
            skipSpaces();
            if (consume('+')) {
                result = new Add(result, parseTerm());
            } else if (consume('-')) {
                result = new Sub(result, parseTerm());
            } else {
                return result;
            }
        }
    }

    private Expression parseTerm() {
        Expression result = parseFactor();
        while (true) {
            skipSpaces();
            if (consume('*')) {
                result = new Mul(result, parseFactor());
            } else if (consume('/')) {
                result = new Div(result, parseFactor());
            } else {
                return result;
            }
        }
    }

    private Expression parseFactor() {
        skipSpaces();
        if (consume('(')) {
            Expression result = parseExpression();
            skipSpaces();
            if (!consume(')')) {
                throw error("Missing closing parenthesis");
            }
            return result;
        }
        if (position < text.length() && (Character.isDigit(text.charAt(position))
                || (text.charAt(position) == '-' && hasNextDigit()))) {
            return new Number(parseInteger());
        }
        if (position < text.length() && isIdentifierStart(text.charAt(position))) {
            int start = position++;
            while (position < text.length() && isIdentifierPart(text.charAt(position))) {
                position++;
            }
            return new Variable(text.substring(start, position));
        }
        throw error("Expected number, variable, or '('");
    }

    private int parseInteger() {
        int start = position;
        if (text.charAt(position) == '-') {
            position++;
        }
        while (position < text.length() && Character.isDigit(text.charAt(position))) {
            position++;
        }
        try {
            return Integer.parseInt(text.substring(start, position));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid number", exception);
        }
    }

    private boolean hasNextDigit() {
        return position + 1 < text.length() && Character.isDigit(text.charAt(position + 1));
    }

    private boolean consume(char expected) {
        if (position < text.length() && text.charAt(position) == expected) {
            position++;
            return true;
        }
        return false;
    }

    private void skipSpaces() {
        while (position < text.length() && Character.isWhitespace(text.charAt(position))) {
            position++;
        }
    }

    private boolean isIdentifierStart(char character) {
        return Character.isLetter(character) || character == '_';
    }

    private boolean isIdentifierPart(char character) {
        return Character.isLetterOrDigit(character) || character == '_';
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException(message + " at position " + position);
    }
}
