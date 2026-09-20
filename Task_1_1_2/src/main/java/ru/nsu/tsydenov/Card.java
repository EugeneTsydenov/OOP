package ru.nsu.tsydenov;

public class Card {
    private final Suit suit;
    private final CardName name;

    public Card(Suit suit, CardName name) {
        this.suit = suit;
        this.name = name;
    }

    public int getNominal() {
        return name.getNominal();
    }

    @Override
    public String toString() {
        return suit.toString() + name.toString();
    }
}