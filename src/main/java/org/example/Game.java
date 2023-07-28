package org.example;

import java.util.List;

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

        calculateAndDisplayScores();
    }


    private void playTurn(Player player) {
        table.displayCurrentTable();

        if (player.isHandEmpty()) {
            dealer.dealCardsToPlayer(player);
        }

        dealer.deck().logDeckSize();

        Card playedCard = player.playCard();

        if (!table.getFaceUpCards().isEmpty()
                && (playedCard.equals(table.getFaceUpCards().getLast()) || playedCard.getValue() == Value.JACK)) {
            List<Card> takenCards = table.takeAllCards();
            player.addGainedCards(takenCards);

            // todo: calculate the total points the olayer scored while getting the pile
            if (table.getFaceUpCards().size() == 1) {
                player.addPoints(10);
                System.out.println("***Pişti!*** Player " + player + " took the pile and scored extra points!");
            } else {
                System.out.println("Player " + player + " took the pile!");
            }
        } else {
            table.addCardFaceUp(playedCard);
        }
    }

    private boolean gameOver() {
        return dealer.deck().isEmpty() && humanPlayer.isHandEmpty() && botPlayer.isHandEmpty();
    }

    private void calculateAndDisplayScores() {
    }
}
