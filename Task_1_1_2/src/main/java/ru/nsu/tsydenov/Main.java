package ru.nsu.tsydenov;

/**
 * The main entry point for starting the Blackjack application.
 */
public class Main {

    /**
     * Constructs a new {@code Main} instance.
     */
    public Main() {
    }

    /**
     * Initializes the deck, player, dealer, and starts the game loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Deck deck = new Deck(1);
        Player player = new Player();
        Dealer dealer = new Dealer(deck);
        Blackjack blackjack = new Blackjack(dealer, player);
        blackjack.start();
    }
}