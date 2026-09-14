package ru.nsu.tsydenov;

public enum CardName {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private final int nominal;

    CardName(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}