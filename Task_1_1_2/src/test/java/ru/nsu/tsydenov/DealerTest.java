package ru.nsu.tsydenov;

import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.card.Rank;
import ru.nsu.tsydenov.card.Suit;
import ru.nsu.tsydenov.participant.Dealer;
import ru.nsu.tsydenov.participant.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the Dealer class.
 *
 */
class DealerTest {

    @Test
    @DisplayName("Test: deal start hand to player and dealer")
    void testDealStartHand() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        player.hitStartHand(new Card(Suit.SPADES, Rank.TWO), new Card(Suit.HEARTS, Rank.THREE));
        dealer.hitStartHand(new Card(Suit.CLUBS, Rank.FOUR), new Card(Suit.DIAMONDS, Rank.FIVE));

        assertEquals(2, player.getHand().size());
        assertEquals(2, dealer.getHand().size());
        assertFalse(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: deal single card to player")
    void testDealCard() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        Card dealtCard = new Card(Suit.SPADES, Rank.ACE);
        player.hit(dealtCard);

        assertNotNull(dealtCard);
        assertEquals(1, player.getHand().size());
        assertEquals(dealtCard, player.getHand().get(0));
    }

    @Test
    @DisplayName("Test: hole card string representation before opening")
    void testHandStringWithHiddenHoleCard() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        dealer.hitStartHand(new Card(Suit.SPADES, Rank.TWO), new Card(Suit.HEARTS, Rank.THREE));

        String handStr = dealer.handString();
        assertTrue(handStr.contains("<hole card>"));
        assertFalse(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: reveal hole card updates state and string")
    void testOpenCloseCard() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        dealer.hitStartHand(new Card(Suit.SPADES, Rank.TWO), new Card(Suit.HEARTS, Rank.THREE));
        Card holeCard = dealer.openCloseCard();

        assertNotNull(holeCard);
        assertTrue(dealer.isOpenedCard());
        assertFalse(dealer.handString().contains("<hole card>"));
    }

    @Test
    @DisplayName("Test: should dealer draw when total under 17")
    void testShouldDealerDrawUnderSeventeen() {
        Dealer dealer = new Dealer();

        dealer.hit(new Card(Suit.SPADES, Rank.TEN));
        dealer.hit(new Card(Suit.HEARTS, Rank.SIX));

        assertTrue(dealer.shouldDealerDraw());
    }

    @Test
    @DisplayName("Test: should not draw when total is 17 or more")
    void testShouldDealerDrawSeventeenOrMore() {
        Dealer dealer = new Dealer();

        // Рука 17 очков (Ten + Seven)
        dealer.hit(new Card(Suit.SPADES, Rank.TEN));
        dealer.hit(new Card(Suit.HEARTS, Rank.SEVEN));

        assertFalse(dealer.shouldDealerDraw());
    }

    @Test
    @DisplayName("Test: discard hand resets cards and hides hole card")
    void testDiscardHandResetsState() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        dealer.hitStartHand(new Card(Suit.SPADES, Rank.TWO), new Card(Suit.HEARTS, Rank.THREE));
        dealer.openCloseCard();
        assertTrue(dealer.isOpenedCard());

        dealer.discardHand();

        assertFalse(dealer.isOpenedCard());
        assertEquals(0, dealer.totalHandNominal());
    }
}