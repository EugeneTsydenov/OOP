package ru.nsu.tsydenov.participant;

import ru.nsu.tsydenov.card.Card;

/**
 * A dealer who plays against the player.
 *
 */
public class Dealer extends Player {
    /** Shows whether the second card is open. */
    private boolean isOpenedCard;

    /** Creates a dealer with a closed second card. */
    public Dealer() {
        super();
        isOpenedCard = false;
    }

    /**
     * Opens the second card.
     *
     * @return the second card
     */
    public Card openCloseCard() {
        isOpenedCard = true;
        return getHand().get(1);
    }

    /**
     * Returns true if the second card is open.
     *
     * @return true if the card is open
     */
    public boolean isOpenedCard() {
        return isOpenedCard;
    }

    /**
     * Returns the dealer's hand as text.
     * If the card is open, both cards are shown.
     * If the card is closed, the second card is hidden.
     *
     * @return dealer's hand as text
     */
    @Override
    public String handString() {
        if (isOpenedCard) {
            return getHand().toString();
        }

        return "[" + getHand().get(0) + ", " + "<hole card>]";
    }

    /**
     * Returns true if the dealer must take another card.
     *
     * @return true if the dealer needs a card
     */
    public boolean shouldDealerDraw() {
        return totalHandNominal() < 17;
    }

    /** Clears the hand and closes the second card. */
    @Override
    public void discardHand() {
        super.discardHand();
        isOpenedCard = false;
    }
}