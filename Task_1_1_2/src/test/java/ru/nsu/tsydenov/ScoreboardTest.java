package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.tsydenov.game.Scoreboard;

/**
 * Tests the Scoreboard class.
 *
 */
class ScoreboardTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String output() {
        return out.toString().trim();
    }

    @Test
    @DisplayName("Test: player wins first round")
    void testPlayerWon() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.playerWon();

        assertEquals("You won the round! The score is 1:0 in your favor.", output());
    }

    @Test
    @DisplayName("Test: dealer wins first round")
    void testDealerWon() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.dealerWon();

        assertEquals("The dealer won the round! The score is 0:1 not in your favor.", output());
    }

    @Test
    @DisplayName("Test: draw on empty scoreboard")
    void testDrawAtStart() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.draw();

        assertEquals("Draw! The score is 0:0 tied.", output());
    }

    @Test
    @DisplayName("Test: draw does not change score")
    void testDrawKeepsScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.playerWon();
        out.reset();

        scoreboard.draw();

        assertEquals("Draw! The score is 1:0 in your favor.", output());
    }

    @Test
    @DisplayName("Test: score becomes tied after equal wins")
    void testTiedScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.playerWon();
        out.reset();

        scoreboard.dealerWon();

        assertEquals("The dealer won the round! The score is 1:1 tied.", output());
    }

    @Test
    @DisplayName("Test: player falls behind after several dealer wins")
    void testPlayerBehind() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.dealerWon();
        scoreboard.dealerWon();
        out.reset();

        scoreboard.playerWon();

        assertEquals("You won the round! The score is 1:2 not in your favor.", output());
    }

    @Test
    @DisplayName("Test: player takes the lead after several wins")
    void testPlayerAhead() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.playerWon();
        scoreboard.playerWon();
        out.reset();

        scoreboard.dealerWon();

        assertEquals("The dealer won the round! The score is 2:1 in your favor.", output());
    }
}