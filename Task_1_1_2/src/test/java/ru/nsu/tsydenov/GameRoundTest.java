package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.card.Rank;
import ru.nsu.tsydenov.card.Suit;
import ru.nsu.tsydenov.game.Deck;
import ru.nsu.tsydenov.game.GameRound;
import ru.nsu.tsydenov.game.Scoreboard;
import ru.nsu.tsydenov.participant.Dealer;
import ru.nsu.tsydenov.participant.Player;

/**
 * Tests the GameRound class.
 *
 */
class GameRoundTest {
    /** Deck that gives cards in a fixed order and does not shuffle. */
    private static class FixedDeck extends Deck {
        private final ArrayList<Card> cards = new ArrayList<Card>();

        FixedDeck(Rank... ranks) {
            super(1);
            for (Rank rank : ranks) {
                cards.add(new Card(Suit.SPADES, rank));
            }
        }

        @Override
        public void resetAndShuffle() {
        }

        @Override
        public Card takeCard() {
            return cards.remove(0);
        }
    }

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;
    private Player player;
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        player = new Player();
        dealer = new Dealer();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private void play(String input, Rank... ranks) {
        GameRound round = new GameRound(
                new FixedDeck(ranks), dealer, player, new Scanner(input), new Scoreboard());
        round.play(1);
    }

    private String output() {
        return out.toString();
    }

    @Test
    @DisplayName("Test: player has blackjack")
    void testPlayerBlackjack() {
        play("", Rank.ACE, Rank.KING, Rank.TEN, Rank.NINE);

        assertTrue(output().contains("You won the round!"));
        assertTrue(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: dealer has blackjack")
    void testDealerBlackjack() {
        play("", Rank.TEN, Rank.NINE, Rank.ACE, Rank.KING);

        assertTrue(output().contains("The dealer won the round!"));
        assertTrue(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: both have blackjack")
    void testBothBlackjack() {
        play("", Rank.ACE, Rank.KING, Rank.ACE, Rank.QUEEN);

        assertTrue(output().contains("Draw!"));
    }

    @Test
    @DisplayName("Test: player busts")
    void testPlayerBust() {
        play("1", Rank.TEN, Rank.SIX, Rank.TWO, Rank.THREE, Rank.KING);

        assertTrue(output().contains("The dealer won the round!"));
        assertEquals(26, player.totalHandNominal());
        assertFalse(dealer.isOpenedCard());
    }

    @Test
    @DisplayName("Test: dealer busts")
    void testDealerBust() {
        play("0", Rank.TEN, Rank.NINE, Rank.TEN, Rank.SIX, Rank.KING);

        assertTrue(output().contains("You won the round!"));
        assertEquals(26, dealer.totalHandNominal());
    }

    @Test
    @DisplayName("Test: player has more points")
    void testPlayerHigher() {
        play("0", Rank.TEN, Rank.NINE, Rank.TEN, Rank.EIGHT);

        assertTrue(output().contains("You won the round!"));
    }

    @Test
    @DisplayName("Test: dealer has more points")
    void testDealerHigher() {
        play("0", Rank.TEN, Rank.SEVEN, Rank.TEN, Rank.NINE);

        assertTrue(output().contains("The dealer won the round!"));
    }

    @Test
    @DisplayName("Test: equal points is a draw")
    void testDraw() {
        play("0", Rank.TEN, Rank.EIGHT, Rank.TEN, Rank.EIGHT);

        assertTrue(output().contains("Draw!"));
    }

    @Test
    @DisplayName("Test: dealer stops on 17")
    void testDealerStopsOnSeventeen() {
        play("0", Rank.TEN, Rank.EIGHT, Rank.TEN, Rank.SEVEN);

        assertEquals(2, dealer.getHand().size());
        assertTrue(output().contains("You won the round!"));
    }

    @Test
    @DisplayName("Test: dealer draws on 16")
    void testDealerDrawsOnSixteen() {
        play("0", Rank.TEN, Rank.NINE, Rank.TEN, Rank.SIX, Rank.FIVE);

        assertEquals(3, dealer.getHand().size());
        assertEquals(21, dealer.totalHandNominal());
        assertTrue(output().contains("The dealer won the round!"));
    }

    @Test
    @DisplayName("Test: player takes several cards")
    void testPlayerTakesSeveralCards() {
        play("1 1 0", Rank.TWO, Rank.THREE, Rank.TEN, Rank.NINE, Rank.FOUR, Rank.FIVE);

        assertEquals(4, player.getHand().size());
        assertEquals(14, player.totalHandNominal());
        assertTrue(output().contains("The dealer won the round!"));
    }

    @Test
    @DisplayName("Test: 21 with three cards is not blackjack")
    void testTwentyOneWithThreeCards() {
        play("1 0", Rank.FIVE, Rank.SIX, Rank.TEN, Rank.NINE, Rank.TEN);

        assertEquals(21, player.totalHandNominal());
        assertFalse(player.isBlackjack());
        assertTrue(output().contains("You won the round!"));
    }

    @Test
    @DisplayName("Test: ace becomes 1 point instead of busting")
    void testSoftAce() {
        play("1 0", Rank.ACE, Rank.SIX, Rank.TEN, Rank.EIGHT, Rank.KING);

        assertEquals(17, player.totalHandNominal());
        assertFalse(player.isBusted());
        assertTrue(output().contains("The dealer won the round!"));
    }

    @Test
    @DisplayName("Test: invalid input is ignored")
    void testInvalidInput() {
        play("abc 5 0", Rank.TEN, Rank.NINE, Rank.TEN, Rank.EIGHT);

        assertTrue(output().contains("Invalid input"));
        assertEquals(2, player.getHand().size());
    }

    @Test
    @DisplayName("Test: round number is printed")
    void testRoundNumber() {
        GameRound round = new GameRound(
                new FixedDeck(Rank.ACE, Rank.KING, Rank.TEN, Rank.NINE),
                dealer, player, new Scanner(""), new Scoreboard());

        round.play(5);

        assertTrue(output().contains("Round 5"));
    }

    @Test
    @DisplayName("Test: dealer card is hidden before dealer turn")
    void testHoleCardHidden() {
        play("1", Rank.TEN, Rank.SIX, Rank.TEN, Rank.NINE, Rank.KING);

        assertTrue(output().contains("<hole card>"));
    }
}