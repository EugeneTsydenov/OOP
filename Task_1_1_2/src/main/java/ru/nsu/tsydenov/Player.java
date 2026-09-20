package ru.nsu.tsydenov;

import java.util.ArrayList;

public class Player {
    protected final ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    public void hit(Card card) {
        hand.add(card);
    }

    public int totalHandNominal() {
        int totalNominal = 0;
        for (Card card: hand) {
            totalNominal += card.getNominal();
        }

        return totalNominal;
    }
}
