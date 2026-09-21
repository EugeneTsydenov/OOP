package ru.nsu.tsydenov;

import java.util.ArrayList;

public class Player {
    protected final Card[] startHand = new Card[2];
    protected final ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    public void hitStartHand(Card card1, Card card2) {
        startHand[0] = card1;
        startHand[1] = card2;
        hand.add(card1);
        hand.add(card2);
    }

    public void hit(Card card) {
        hand.add(card);
    }

    public int totalHandNominal() {
        int totalNominal = 0;
        int aceCount = 0;

        for (Card card : hand) {
            int val = card.getNominal();
            totalNominal += val;
            if (card.getName() == CardName.ACE) {
                aceCount++;
            }
        }

        while (totalNominal > 21 && aceCount > 0) {
            totalNominal -= 10;
            aceCount--;
        }

        return totalNominal;
    }


    public String handString() {
        return hand.toString();
    }

    public boolean isBlackjack() {
        return hand.size() == 2 && totalHandNominal() == 21;
    }

    public boolean isBusted() {
        return totalHandNominal() > 21;
    }

    public void discardHand() {
        hand.clear();
    }
}
