package ru.nsu.tsydenov.game;

import ru.nsu.tsydenov.participant.Dealer;
import ru.nsu.tsydenov.participant.Player;

/**
 * Starts the Blackjack game.
 *
 */
public class Main {

    /** Creates the main class object. */
    public Main() {
    }

    /**
     * Starts the game.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        Deck deck = new Deck(1);
        Player player = new Player();
        Dealer dealer = new Dealer();
        Blackjack blackjack = new Blackjack(dealer, player);
        blackjack.start();
    }
}