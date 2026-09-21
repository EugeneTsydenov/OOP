package ru.nsu.tsydenov;

public class Dealer extends Player {
    private Deck deck;
    private boolean isOpenedCard;

    public Dealer(Deck deck) {
        super();
        this.deck = deck;
        isOpenedCard = false;
    }

    public void startNewRound() {
        deck.resetAndShuffle();
    }

    public void dealStartHand(Player player) {
        player.hitStartHand(deck.takeCard(), deck.takeCard());
        this.hitStartHand(deck.takeCard(), deck.takeCard());
    }

    public Card deal(Player receiverPlayer) {
        Card card = deck.takeCard();
        receiverPlayer.hit(card);
        return card;
    }

    public Card openCloseCard() {
        isOpenedCard = true;
        return startHand[1];
    }

    public boolean isOpenedCard() {
        return isOpenedCard;
    }

    @Override
    public String handString() {
        if (isOpenedCard) {
            return hand.toString();
        }

        return "[" + startHand[0] + ", " + "<hole card>]";
    }

    public boolean shouldDealerDraw() {
        return totalHandNominal() <  17;
    }

    @Override
    public void discardHand() {
        super.discardHand();
        isOpenedCard = false;
    }
}
