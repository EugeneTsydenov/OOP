package ru.nsu.tsydenov;

/**
 * Represents the dealer in a Blackjack game.
 *
 * <p>
 * Extends {@link Player} and manages dealing cards from a {@link Deck},
 * revealing the hidden face-down card ("hole card"), and following standard dealer drawing rules.
 * </p>
 *
 */
public class Dealer extends Player {
    /** The deck shoe used by the dealer to deal cards. */
    private final Deck deck;

    /** Indicates whether the dealer's hidden hole card has been revealed. */
    private boolean isOpenedCard;

    /**
     * Creates a new dealer with a specified deck shoe.
     *
     * @param deck the {@link Deck} to deal cards from
     */
    public Dealer(Deck deck) {
        super();
        this.deck = deck;
        isOpenedCard = false;
    }

    /**
     * Resets and reshuffles the deck shoe to start a new round.
     */
    public void startNewRound() {
        deck.resetAndShuffle();
    }

    /**
     * Deals two initial cards to both the player and the dealer.
     *
     * @param player the player receiving the initial hand
     */
    public void dealStartHand(Player player) {
        player.hitStartHand(deck.takeCard(), deck.takeCard());
        this.hitStartHand(deck.takeCard(), deck.takeCard());
    }

    /**
     * Deals a single card from the deck to a receiving player.
     *
     * @param receiverPlayer the player receiving the card
     * @return the drawn {@link Card}
     */
    public Card deal(Player receiverPlayer) {
        Card card = deck.takeCard();
        receiverPlayer.hit(card);
        return card;
    }

    /**
     * Reveals the dealer's hidden second card (hole card).
     *
     * @return the revealed hidden {@link Card}
     */
    public Card openCloseCard() {
        isOpenedCard = true;
        return startHand[1];
    }

    /**
     * Checks if the dealer's hidden card has been revealed.
     *
     * @return {@code true} if the hidden card is open, {@code false} otherwise
     */
    public boolean isOpenedCard() {
        return isOpenedCard;
    }

    /**
     * Returns a string representation of the dealer's hand.
     *
     * <p>
     * If the hidden card is revealed,
     * returns all cards; otherwise hides the second card as {@code <hole card>}.
     * </p>
     *
     * @return formatted string of the dealer's hand
     */
    @Override
    public String handString() {
        if (isOpenedCard) {
            return hand.toString();
        }

        return "[" + startHand[0] + ", " + "<hole card>]";
    }

    /**
     * Checks if the dealer must draw another card based on standard Blackjack rules.
     *
     * @return {@code true} if the dealer's hand total is under 17, {@code false} otherwise
     */
    public boolean shouldDealerDraw() {
        return totalHandNominal() < 17;
    }

    /**
     * Resets the dealer's hand and hides the face-down card state for the next round.
     */
    @Override
    public void discardHand() {
        super.discardHand();
        isOpenedCard = false;
    }
}