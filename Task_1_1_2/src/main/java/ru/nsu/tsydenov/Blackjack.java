package ru.nsu.tsydenov;

import java.util.Scanner;

/**
 * Manages the main game logic, rounds, score tracking, and user interactions for a Blackjack game.
 *
 * @author Tsydenov
 * @version 1.0
 * @since 1.0
 */
public class Blackjack {
    /** Scanner instance used to read player input from the console. */
    private final Scanner scanner = new Scanner(System.in);

    /** The dealer instance managing the cards. */
    private final Dealer dealer;

    /** The human player instance. */
    private final Player player;

    /** Tracks the total number of rounds played. */
    private int roundCnt;

    /** Tracks the total number of rounds won by the dealer. */
    private int dealerWinsCnt;

    /** Tracks the total number of rounds won by the player. */
    private int playerWinsCnt;

    /**
     * Creates a new Blackjack game controller with a dealer and a player.
     *
     * @param dealer the dealer managing the deck and deals
     * @param player the player participating in the game
     */
    public Blackjack(Dealer dealer, Player player) {
        this.dealer = dealer;
        this.player = player;
    }

    /**
     * Starts the main game loop, running rounds continuously and resetting hands between rounds.
     */
    public void start() {
        System.out.print("Welcome to Blackjack!\n");
        while (true) {
            round();
            System.out.print("\n\n\n-------");
            player.discardHand();
            dealer.discardHand();
        }
    }

    /**
     * Runs a single round of Blackjack from dealing initial cards to declaring the winner.
     */
    private void round() {
        System.out.printf("Round %d\n", roundCnt);
        roundCnt++;
        dealer.startNewRound();
        dealer.dealStartHand(player);
        System.out.print("The dealer dealt the cards\n");
        printPlayerAndDealerHands();

        if (player.isBlackjack() && dealer.isBlackjack()) {
            dealer.openCloseCard();
            printPlayerAndDealerHands();
            draw();
            return;
        }

        if (player.isBlackjack()) {
            playerWin();
            return;
        }

        if (dealer.isBlackjack()) {
            dealer.openCloseCard();
            printPlayerAndDealerHands();
            dealerWin();
            return;
        }

        System.out.print("Your move\n");
        System.out.print("--------\n");
        playerMoves();

        if (player.isBusted()) {
            dealerWin();
            return;
        }

        System.out.print("The dealer move\n");
        System.out.print("--------\n");
        dealerMoves();

        if (dealer.isBusted()) {
            playerWin();
            return;
        }

        if (player.totalHandNominal() > dealer.totalHandNominal()) {
            playerWin();
        } else if (player.totalHandNominal() < dealer.totalHandNominal()) {
            dealerWin();
        } else {
            draw();
        }
    }

    /**
     * Prints current cards and point totals for both the player and the dealer to the console.
     */
    private void printPlayerAndDealerHands() {
        System.out.printf("\tYour hand: %s > %d\n", player.handString(), player.totalHandNominal());
        if (dealer.isOpenedCard()) {
            System.out.printf("\tDealer hand: %s > %d\n", dealer.handString(), dealer.totalHandNominal());
        } else {
            System.out.printf("\tDealer hand: %s\n", dealer.handString());
        }
    }

    /**
     * Handles the end of a round when both scores are equal (Push/Draw) and prints the score message.
     */
    private void draw() {
        if (playerWinsCnt == dealerWinsCnt) {
            System.out.printf("Draw! The score is %s:%s tied.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        if (playerWinsCnt < dealerWinsCnt) {
            System.out.printf("Draw! The score is %s:%s not in your favor.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        System.out.printf("Draw! The score is %s:%s in your favor.\n", playerWinsCnt, dealerWinsCnt);
    }

    /**
     * Handles a player victory, increments player wins, and prints the updated match score.
     */
    private void playerWin() {
        playerWinsCnt++;
        if (playerWinsCnt == dealerWinsCnt) {
            System.out.printf("You won the round! The score is %s:%s tied.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        if (playerWinsCnt < dealerWinsCnt) {
            System.out.printf("You won the round! The score is %s:%s not in your favor.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        System.out.printf("You won the round! The score is %s:%s in your favor.\n", playerWinsCnt, dealerWinsCnt);
    }

    /**
     * Handles a dealer victory, increments dealer wins, and prints the updated match score.
     */
    private void dealerWin() {
        dealerWinsCnt++;
        if (playerWinsCnt == dealerWinsCnt) {
            System.out.printf("The dealer won the round! The score is %s:%s tied.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        if (playerWinsCnt < dealerWinsCnt) {
            System.out.printf("The dealer won the round! The score is %s:%s not in your favor.\n", playerWinsCnt, dealerWinsCnt);
            return;
        }

        System.out.printf("The dealer won the round! The score is %s:%s in your favor.\n", playerWinsCnt, dealerWinsCnt);
    }

    /**
     * Loops to receive user input during the player's turn until they stop ("0") or bust.
     */
    private void playerMoves() {
        String input;

        while (true) {
            System.out.print("Enter “1” to take a card and “0” to stop...\n");
            input = scanner.next();
            if (!input.equals("0") && !input.equals("1")) {
                System.out.print("Invalid input. Try again.\n");
                continue;
            }

            if (input.equals("0")) {
                return;
            }

            playerMove();
            if (player.isBusted()) {
                return;
            }
        }
    }

    /**
     * Draws a single card for the player and displays updated hands.
     */
    private void playerMove() {
        Card openedCard = dealer.deal(player);
        System.out.printf("You reveal the %s card.\n", openedCard);
        printPlayerAndDealerHands();
    }

    /**
     * Executes the dealer's turn by revealing the face-down card and drawing until reaching at least 17 points.
     */
    private void dealerMoves() {
        Card openedCard = dealer.openCloseCard();
        System.out.printf("The dealer reveals a face-down card, the %s.\n", openedCard);
        printPlayerAndDealerHands();

        if (dealer.isBusted()) {
            return;
        }

        while (dealer.shouldDealerDraw()) {
            dealerMove();
        }
    }

    /**
     * Draws a single card for the dealer and displays updated hands.
     */
    private void dealerMove() {
        Card openedCard = dealer.deal(dealer);
        System.out.printf("The dealer reveals the %s card.\n", openedCard);
        printPlayerAndDealerHands();
    }
}