package ru.nsu.tsydenov.game;

import java.util.Scanner;
import ru.nsu.tsydenov.participant.Dealer;
import ru.nsu.tsydenov.participant.Player;

/**
 * Controls the Blackjack game.
 *
 * @author Tsydenov
 * @version 1.0
 */
public class Blackjack {
    private final Scanner scanner;
    private final Dealer dealer;
    private final Player player;
    private final Deck deck;
    private final Scoreboard scoreboard;

    /**
     * Creates a game with one deck and console input.
     *
     * @param dealer game dealer
     * @param player game player
     */
    public Blackjack(Dealer dealer, Player player) {
        this(dealer, player, new Deck(1), new Scanner(System.in));
    }

    /**
     * Creates a game with the given objects.
     *
     * @param dealer game dealer
     * @param player game player
     * @param deck game deck
     * @param scanner input scanner
     */
    public Blackjack(Dealer dealer, Player player, Deck deck, Scanner scanner) {
        this.dealer = dealer;
        this.player = player;
        this.deck = deck;
        this.scanner = scanner;
        scoreboard = new Scoreboard();
    }

    /** Starts the game rounds. */
    public void start() {
        System.out.println("Welcome to Blackjack!");
        while (true) {
            GameRound round = new GameRound(deck, dealer, player, scanner, scoreboard);
            round.play();
            player.discardHand();
            dealer.discardHand();
            System.out.println("\n\n\n-------");
        }
    }
}
