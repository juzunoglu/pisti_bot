package org.example;

import java.util.Scanner;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Table table;
    private final Dealer dealer;
    private final Scanner scanner;


    public Game(Player player1, Player player2, Dealer dealer,
                Table table, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.dealer = dealer;
        this.table = table;
        this.scanner = scanner;
    }

    public void start() {
        dealer.dealInitialCards(player1, player2, table);

        while (!gameOver()) {
            playTurn(player1);
            if (gameOver()) break;
            playTurn(player2);
        }

        // Once the game is over, calculate and display the scores
        calculateAndDisplayScores();
    }


    private Card getPlayerInput() {
        System.out.println("Enter the number corresponding to the suit of the card you want to play:");
        for (int i = 0; i < Suit.values().length; i++) {
            System.out.println(i + ": " + Suit.values()[i]);
        }
        int suitIndex = scanner.nextInt();

        System.out.println("Enter the number corresponding to the value of the card you want to play:");
        for (int i = 0; i < Value.values().length; i++) {
            System.out.println(i + ": " + Value.values()[i]);
        }
        int valueIndex = scanner.nextInt();

        return new Card(Suit.values()[suitIndex], Value.values()[valueIndex]);
    }

    private void playTurn(Player player) {
        // Displaying the table state
        System.out.println("Current table state: ");
        table.display();


        if (player.isHandEmpty()) {
            dealer.dealCardsToPlayer(player);
        }

        // Displaying player's hand
        System.out.println("Your hand: ");
        player.displayHand();

        Card playedCard = null;

        if (player.equals(player1)) {
            boolean validCardPlayed = false;
            while (!validCardPlayed) {
                try {
                    Card selectedCard = getPlayerInput();
                    playedCard = player.playCard(selectedCard);
                    validCardPlayed = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Card not in hand. Please try again.");
                }
            }
        } else {
            // Bot player's turn...
            System.out.println("Robot will play here");
        }

        table.addCardFaceUp(playedCard);
    }

    private boolean gameOver() {
        // Add game ending condition here.
        // For example, the game ends when the deck is empty and both players have no cards left.
        return dealer.getDeck().isEmpty() && player1.isHandEmpty() && player2.isHandEmpty();
    }

    private void calculateAndDisplayScores() {
        // You would implement this method to calculate the final scores and display them.
    }
}
