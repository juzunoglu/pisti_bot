package org.example;

import org.example.bot.BotPlayer;

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

        // todo: should be parametric as to who goes first.
        while (gameInProgress()) {
            playTurnHuman();
            if (gameOver()) break;
            playTurnBot();
        }
        assignExtraPointsForMostGainedCards();
        determineWinner();
    }


    private void playTurnHuman() {

        System.out.println("*********************Human Player Turn*****************");
        table.displayCurrentTable();

        if (humanPlayer.isHandEmpty()) {
            dealer.dealCardsToPlayer(humanPlayer);
        }

        dealer.logDeckSize();

        Card playedCard = humanPlayer.playCard();

        resolveTurn(humanPlayer, playedCard);
    }

    private void playTurnBot() {
        System.out.println("*********************Bot Player Turn*****************");
        table.displayCurrentTable();

        if (botPlayer.isHandEmpty()) {
            dealer.dealCardsToPlayer(botPlayer);
        }

        Card playedCard = botPlayer.playCard();

        resolveTurn(botPlayer, playedCard);
    }

    // todo: make this more readable.
    private void resolveTurn(Player player, Card playedCard) {
        botPlayer.rememberCard(playedCard);
        botPlayer.logSeenCards();

        if (isTableEmpty()) {
            table.addCardFaceUp(playedCard);
        } else if (isMatchingCard(playedCard) || isJack(playedCard)) {
            player.addGainedCards(table.getCurrentPile());
            player.addPoints(table.getCurrentPile());
            checkAndHandlePisti(player, playedCard);
            table.removeAllCards();
        } else {
            table.addCardFaceUp(playedCard);
        }
    }

    private boolean isTableEmpty() {
        return table.getFaceUpCards().isEmpty();
    }

    private boolean isMatchingCard(Card playedCard) {
        return playedCard.getValue().equals(table.getFaceUpCards().getLast().getValue());
    }

    private boolean isJack(Card playedCard) {
        return playedCard.getValue() == Value.JACK;
    }

    private void checkAndHandlePisti(Player player, Card playedCard) {
        if (table.getCurrentPile().size() == 1 && !isJack(playedCard) && !isJack(table.getPileTopCard().get())) {
            player.addPoints(10);
            System.out.println("***Pişti!*** Player " + player + " took the pile and scored extra points!");
        } else if (table.getCurrentPile().size() == 1 && isJack(playedCard) && isJack(table.getPileTopCard().get())) {
            player.addPoints(20);
            System.out.println("***Pişti!*** Player " + player + " took the pile with a Jack and scored extra points!");
        } else {
            System.out.println("Player " + player + " took the pile without a pişti!");
        }
    }

    private boolean gameOver() {
        return dealer.deck().isEmpty() && humanPlayer.isHandEmpty() && botPlayer.isHandEmpty();
    }

    private boolean gameInProgress() {
        return !(dealer.deck().isEmpty() && humanPlayer.isHandEmpty() && botPlayer.isHandEmpty());
    }

    private void determineWinner() {
        System.out.println("Score for human player: " + humanPlayer.getScore());
        System.out.println("Score for botPlayer: " + botPlayer.getScore());

        if (humanPlayer.getScore() > botPlayer.getScore()) {
            System.out.println("Human win");
            return;
        }
        System.out.println("Bot win");
    }

    public void assignExtraPointsForMostGainedCards() {
        int player1GainedCards = humanPlayer.getGainedCards().size();
        int player2GainedCards = botPlayer.getGainedCards().size();

        if (player1GainedCards > player2GainedCards) {
            humanPlayer.addPoints(3);
        } else if (player2GainedCards > player1GainedCards) {
            botPlayer.addPoints(3);
        }
    }
}
