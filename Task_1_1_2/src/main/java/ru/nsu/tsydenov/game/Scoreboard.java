package ru.nsu.tsydenov.game;

/**
 * Stores the number of wins in the game.
 *
 */
public class Scoreboard {
    private int playerWins;
    private int dealerWins;

    /** Adds a player win and prints the score. */
    public void playerWon() {
        playerWins++;
        print("You won the round!");
    }

    /** Adds a dealer win and prints the score. */
    public void dealerWon() {
        dealerWins++;
        print("The dealer won the round!");
    }

    /** Prints a draw and the current score. */
    public void draw() {
        print("Draw!");
    }

    private void print(String message) {
        String relation = playerWins == dealerWins
                ? "tied"
                : playerWins > dealerWins ? "in your favor" : "not in your favor";
        System.out.printf("%s The score is %s:%s %s.%n",
                message, playerWins, dealerWins, relation);
    }
}
