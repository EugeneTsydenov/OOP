package ru.nsu.tsydenov;

import java.util.Scanner;

public class Blackjack {
    private final Scanner scanner = new Scanner(System.in);

    private final Dealer dealer;
    private final Player player;
    private int roundCnt;
    private int dealerWinsCnt;
    private int playerWinsCnt;

    public Blackjack(Dealer dealer, Player player) {
        this.dealer = dealer;
        this.player = player;
    }

    public void start() {
        System.out.print("Welcome to Blackjack!\n");
        while (true) {
            round();
            System.out.print("\n\n\n-------");
            player.discardHand();
            dealer.discardHand();
        }
    }

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

    private void printPlayerAndDealerHands() {
        System.out.printf("\tYour hand: %s > %d\n", player.handString(), player.totalHandNominal());
        if (dealer.isOpenedCard()) {
            System.out.printf("\tDealer hand: %s > %d\n", dealer.handString(), dealer.totalHandNominal());
        } else {
            System.out.printf("\tDealer hand: %s\n", dealer.handString());
        }
    }

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

    private void playerMove() {
        Card openedCard = dealer.deal(player);
        System.out.printf("You reveal the %s card.\n", openedCard);
        printPlayerAndDealerHands();
    }

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

    private void dealerMove() {
        Card openedCard = dealer.deal(dealer);
        System.out.printf("The dealer reveals the %s card.\n", openedCard);
        printPlayerAndDealerHands();
    }
}
