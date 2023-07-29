package org.example;

import org.example.bot.BotPlayer;

public class Game {

    public static final int NORMAL_PISTI_SCORE = 10;
    public static final int PISTI_WITH_JACK_SCORE = 20;
    private final HumanPlayer humanPlayer;
    private final BotPlayer botPlayer;
    private final Table table;
    private final Dealer dealer;

    private final Scoreboard scoreboard;

    public Game(HumanPlayer humanPlayer, BotPlayer botPlayer, Dealer dealer,
                Table table, Scoreboard scoreboard) {
        this.humanPlayer = humanPlayer;
        this.botPlayer = botPlayer;
        this.dealer = dealer;
        this.table = table;
        this.scoreboard = scoreboard;
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
            int pointsFromCards = player.getPointsFromCards(table.getCurrentPile());
            scoreboard.addScore(player, pointsFromCards);
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
            scoreboard.addScore(player, NORMAL_PISTI_SCORE);
            System.out.println("***Pişti!*** Player " + player + " took the pile and scored extra points!");
        } else if (table.getCurrentPile().size() == 1 && isJack(playedCard) && isJack(table.getPileTopCard().get())) {
            scoreboard.addScore(player, PISTI_WITH_JACK_SCORE);
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
        System.out.println("Score for human player: " + scoreboard.getScore(humanPlayer));
        System.out.println("Score for botPlayer: " + scoreboard.getScore(botPlayer));

        System.out.println("Scoreboard: " + scoreboard);
        if (scoreboard.getScore(humanPlayer) > scoreboard.getScore(botPlayer)) {
            System.out.println("Human win");
        } else if (scoreboard.getScore(botPlayer) > scoreboard.getScore(humanPlayer)) {
            System.out.println("Bot win");
        } else {
            System.out.println("It's a draw");
        }
    }

    public void assignExtraPointsForMostGainedCards() {
        int player = humanPlayer.getGainedCards().size();
        int bot = botPlayer.getGainedCards().size();

        if (player > bot) {
            scoreboard.addScore(humanPlayer, 3);
        } else if (bot > player) {
            scoreboard.addScore(botPlayer, 3);
        }
    }
}
