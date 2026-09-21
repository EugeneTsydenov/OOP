package ru.nsu.tsydenov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    @DisplayName("Test: deal start hand to player and dealer")
    void testDealStartHand() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);
        Player player = new Player();

        dealer.dealStartHand(player);

        assertEquals(2, player.hand.size());
        assertEquals(2, dealer.hand.size());
        assertFalse(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: deal single card to player")
    void testDealCard() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);
        Player player = new Player();

        Card dealtCard = dealer.deal(player);

        assertNotNull(dealtCard);
        assertEquals(1, player.hand.size());
        assertEquals(dealtCard, player.hand.get(0));
    }

    @Test
    @DisplayName("Test: hole card string representation before opening")
    void testHandStringWithHiddenHoleCard() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);
        Player player = new Player();

        dealer.dealStartHand(player);

        String handStr = dealer.handString();
        assertTrue(handStr.contains("<hole card>"));
        assertFalse(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: reveal hole card updates state and string")
    void testOpenCloseCard() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);
        Player player = new Player();

        dealer.dealStartHand(player);
        Card holeCard = dealer.openCloseCard();

        assertNotNull(holeCard);
        assertTrue(dealer.isOpenedCard());
        assertFalse(dealer.handString().contains("<hole card>"));
    }

    @Test
    @DisplayName("Test: should dealer draw when total under 17")
    void testShouldDealerDrawUnderSeventeen() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);

        dealer.hit(new Card(Suit.SPADES, Rank.TEN));
        dealer.hit(new Card(Suit.HEARTS, Rank.SIX));

        assertTrue(dealer.shouldDealerDraw());
    }

    @Test
    @DisplayName("Test: should not draw when total is 17 or more")
    void testShouldDealerDrawSeventeenOrMore() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);

        // Рука 17 очков (Ten + Seven)
        dealer.hit(new Card(Suit.SPADES, Rank.TEN));
        dealer.hit(new Card(Suit.HEARTS, Rank.SEVEN));

        assertFalse(dealer.shouldDealerDraw());
    }

    @Test
    @DisplayName("Test: discard hand resets cards and hides hole card")
    void testDiscardHandResetsState() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer(deck);
        Player player = new Player();

        dealer.dealStartHand(player);
        dealer.openCloseCard();
        assertTrue(dealer.isOpenedCard());

        dealer.discardHand();

        assertFalse(dealer.isOpenedCard());
        assertEquals(0, dealer.totalHandNominal());
    }
}