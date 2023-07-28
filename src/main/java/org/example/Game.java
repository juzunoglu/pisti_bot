package org.example;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Table table;
    private final Dealer dealer;


    public Game(Player player1, Player player2, Dealer dealer,
                Table table) {
        this.player1 = player1;
        this.player2 = player2;
        this.dealer = dealer;
        this.table = table;
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


    private void playTurn(Player player) {
        table.displayCurrentTable();

        if (player.isHandEmpty()) {
            dealer.dealCardsToPlayer(player);
        }


        Card playedCard = player.playCard();

        // Bot player's turn...
        System.out.println("Bot player has played a card");

        table.addCardFaceUp(playedCard);

        // Add rules to handle special cards and scoring here.
        // For example, if the played card matches the last face up card on the table,
        // player gets the pile, etc.
    }


    private boolean gameOver() {
        // Add game ending condition here.
        // For example, the game ends when the deck is empty and both players have no cards left.
        return dealer.deck().isEmpty() && player1.isHandEmpty() && player2.isHandEmpty();
    }

    private void calculateAndDisplayScores() {
        // You would implement this method to calculate the final scores and display them.
    }
}
