package ru.nsu.tsydenov;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck(1);
        Player player = new Player();
        Dealer dealer = new Dealer(deck);
        Blackjack blackjack = new Blackjack(dealer, player);
        blackjack.start();
    }
}