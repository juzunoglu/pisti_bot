package org.example;

public class Game {
    private final HumanPlayer humanPlayer;
    private final BotPlayer botPlayer;
    private final Table table;
    private final Dealer dealer;


    public Game(HumanPlayer humanPlayer, BotPlayer botPlayer, Dealer dealer,
                Table table) {
        this.humanPlayer = humanPlayer;
        this.botPlayer = botPlayer;
        this.dealer = dealer;
        this.table = table;
    }

    public void start() {
        dealer.dealInitialCards(humanPlayer, botPlayer, table);

        while (!gameOver()) {
            playTurn(humanPlayer);
            if (gameOver()) break;
            playTurn(botPlayer);
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

        table.addCardFaceUp(playedCard);

        // Add rules to handle special cards and scoring here.
        // For example, if the played card matches the last face up card on the table,
        // player gets the pile, etc.
    }


    private boolean gameOver() {
        // Add game ending condition here.
        return dealer.deck().isEmpty() && humanPlayer.isHandEmpty() && botPlayer.isHandEmpty();
    }

    private void calculateAndDisplayScores() {
    }
}
