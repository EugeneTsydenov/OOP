package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.card.Rank;
import ru.nsu.tsydenov.card.Suit;
import ru.nsu.tsydenov.participant.Player;

/**
 * Tests the Player class.
 *
 */
class PlayerTest {

    @Test
    @DisplayName("Test: initial hand setup")
    void testHitStartHand() {
        Player player = new Player();
        Card c1 = new Card(Suit.SPADES, Rank.TEN);
        Card c2 = new Card(Suit.HEARTS, Rank.SEVEN);

        player.hitStartHand(c1, c2);

        assertEquals(17, player.totalHandNominal());
        assertFalse(player.isBlackjack());
        assertFalse(player.isBusted());
    }

    @Test
    @DisplayName("Test: hit single card")
    void testHit() {
        Player player = new Player();
        player.hit(new Card(Suit.CLUBS, Rank.FIVE));
        player.hit(new Card(Suit.DIAMONDS, Rank.SIX));

        assertEquals(11, player.totalHandNominal());
    }

    @Test
    @DisplayName("Test: natural blackjack detection")
    void testIsBlackjack() {
        Player player = new Player();
        player.hitStartHand(new Card(Suit.SPADES, Rank.ACE), new Card(Suit.HEARTS, Rank.KING));

        assertTrue(player.isBlackjack());
    }

    @Test
    @DisplayName("Test: 21 points with 3 cards is not natural blackjack")
    void testTwentyOneWithThreeCardsIsNotBlackjack() {
        Player player = new Player();
        player.hit(new Card(Suit.SPADES, Rank.SEVEN));
        player.hit(new Card(Suit.HEARTS, Rank.SEVEN));
        player.hit(new Card(Suit.CLUBS, Rank.SEVEN));

        assertEquals(21, player.totalHandNominal());
        assertFalse(player.isBlackjack());
    }

    @Test
    @DisplayName("Test: ace value reduces from 11 to 1 when total exceeds 21")
    void testAceReduction() {
        Player player = new Player();

        player.hit(new Card(Suit.SPADES, Rank.ACE));
        player.hit(new Card(Suit.HEARTS, Rank.EIGHT));
        assertEquals(19, player.totalHandNominal());

        player.hit(new Card(Suit.CLUBS, Rank.FIVE));
        assertEquals(14, player.totalHandNominal());
        assertFalse(player.isBusted());
    }

    @Test
    @DisplayName("Test: multiple aces reduction")
    void testMultipleAces() {
        Player player = new Player();

        player.hit(new Card(Suit.SPADES, Rank.ACE));
        player.hit(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(12, player.totalHandNominal());
        assertFalse(player.isBusted());
    }

    @Test
    @DisplayName("Test: player busted check")
    void testIsBusted() {
        Player player = new Player();
        player.hit(new Card(Suit.SPADES, Rank.TEN));
        player.hit(new Card(Suit.HEARTS, Rank.JACK));
        player.hit(new Card(Suit.CLUBS, Rank.FIVE));

        assertTrue(player.isBusted());
    }

    @Test
    @DisplayName("Test: discard hand clears cards")
    void testDiscardHand() {
        Player player = new Player();
        player.hitStartHand(new Card(Suit.SPADES, Rank.KING), new Card(Suit.HEARTS, Rank.QUEEN));
        player.discardHand();

        assertEquals(0, player.totalHandNominal());
        assertFalse(player.isBusted());
    }

    @Test
    @DisplayName("Test: handString returns list representation")
    void testHandString() {
        Player player = new Player();
        player.hit(new Card(Suit.SPADES, Rank.ACE));

        assertEquals("[Ace of Spades (11)]", player.handString());
    }
}