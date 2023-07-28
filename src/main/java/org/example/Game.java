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
            playTurnHuman();
            if (gameOver()) break;
            playTurnBot();
        }

        calculateAndDisplayScores();
    }


    private void playTurnHuman() {
        table.displayCurrentTable();

        if (humanPlayer.isHandEmpty()) {
            dealer.dealCardsToPlayer(humanPlayer);
        }

        dealer.deck().logDeckSize();

        Card playedCard = humanPlayer.playCard();

        resolveTurn(humanPlayer, playedCard);
    }

    private void playTurnBot() {
        if (botPlayer.isHandEmpty()) {
            dealer.dealCardsToPlayer(botPlayer);
        }

        Card playedCard = botPlayer.playCard();

        resolveTurn(botPlayer, playedCard);
    }

    private void resolveTurn(Player player, Card playedCard) {
        if (!table.getFaceUpCards().isEmpty()
                && (playedCard.equals(table.getFaceUpCards().getLast()) || playedCard.getValue() == Value.JACK)) {
            List<Card> takenCards = table.takeAllCards();
            player.addGainedCards(takenCards);

            // Calculate the total points the player scored while getting the pile
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
