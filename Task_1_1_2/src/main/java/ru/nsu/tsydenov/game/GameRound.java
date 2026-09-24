package ru.nsu.tsydenov.game;

import java.util.Scanner;
import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.participant.Dealer;
import ru.nsu.tsydenov.participant.Player;

/**
 * Controls one game round.
 *
 */
public class GameRound {
    private final Deck deck;
    private final Dealer dealer;
    private final Player player;
    private final Scanner scanner;
    private final Scoreboard scoreboard;

    /**
     * Creates a round with the given objects.
     *
     * @param deck game deck
     * @param dealer game dealer
     * @param player game player
     * @param scanner input scanner
     * @param scoreboard game score
     */
    public GameRound(
            Deck deck, Dealer dealer, Player player, Scanner scanner, Scoreboard scoreboard) {
        this.deck = deck;
        this.dealer = dealer;
        this.player = player;
        this.scanner = scanner;
        this.scoreboard = scoreboard;
    }

    /** Plays the round. */
    public void play() {
        System.out.printf("Round%n");
        deck.resetAndShuffle();
        dealInitialHands();
        printHands();

        if (player.isBlackjack() || dealer.isBlackjack()) {
            dealer.openCloseCard();
            printHands();
            if (player.isBlackjack() && dealer.isBlackjack()) {
                scoreboard.draw();
            } else if (player.isBlackjack()) {
                scoreboard.playerWon();
            } else {
                scoreboard.dealerWon();
            }
            return;
        }

        playerTurn();
        if (player.isBusted()) {
            scoreboard.dealerWon();
            return;
        }

        dealerTurn();
        if (dealer.isBusted()) {
            scoreboard.playerWon();
        } else if (player.totalHandNominal() > dealer.totalHandNominal()) {
            scoreboard.playerWon();
        } else if (player.totalHandNominal() < dealer.totalHandNominal()) {
            scoreboard.dealerWon();
        } else {
            scoreboard.draw();
        }
    }

    private void dealInitialHands() {
        player.hitStartHand(deck.takeCard(), deck.takeCard());
        dealer.hitStartHand(deck.takeCard(), deck.takeCard());
    }

    private void playerTurn() {
        while (true) {
            System.out.print("Enter \"1\" to take a card and \"0\" to stop...");
            String input = scanner.next();
            if ("0".equals(input)) {
                return;
            }
            if (!"1".equals(input)) {
                System.out.print("Invalid input. Try again.");
                continue;
            }
            dealTo(player);
            if (player.isBusted()) {
                return;
            }
        }
    }

    private void dealerTurn() {
        dealer.openCloseCard();
        printHands();
        while (dealer.shouldDealerDraw()) {
            dealTo(dealer);
        }
    }

    private void dealTo(Player receiver) {
        Card card = deck.takeCard();
        receiver.hit(card);
        System.out.printf("%s reveals the %s card.%n",
                receiver == player ? "You" : "The dealer", card);
        printHands();
    }

    private void printHands() {
        System.out.printf("\tYour hand: %s > %d%n",
                player.handString(), player.totalHandNominal());
        System.out.printf("\tDealer hand: %s%s%n",
                dealer.handString(),
                dealer.isOpenedCard() ? " > " + dealer.totalHandNominal() : "");
    }
}
