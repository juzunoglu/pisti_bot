package org.example.gameloop;

import org.example.bot.BotPlayer;
import org.example.bot.BotStrategy;
import org.example.enums.PistiTypes;
import org.example.enums.Value;
import org.example.player.HumanPlayer;
import org.example.player.Player;

import java.util.Map;
import java.util.Scanner;

public class Game {
    public static final int NORMAL_PISTI_SCORE = 10;
    public static final int PISTI_WITH_JACK_SCORE = 20;
    private final HumanPlayer humanPlayer;
    private final BotPlayer botPlayer;
    private final Table table;
    private final Dealer dealer;
    private final Scoreboard scoreboard;
    private final Scanner scanner;
    private Player firstPlayer;

    public Game(HumanPlayer humanPlayer, BotPlayer botPlayer, Dealer dealer,
                Table table, Scoreboard scoreboard, Scanner scanner) {
        this.humanPlayer = humanPlayer;
        this.botPlayer = botPlayer;
        this.dealer = dealer;
        this.table = table;
        this.scoreboard = scoreboard;
        this.scanner = scanner;
    }

    // todo: Bot should be able to see the cards if he wins the faceDownCards from the pile.
    public void start() {
        chooseFirstPlayer(scanner);
        dealer.dealInitialCards(humanPlayer, botPlayer, table);
        botPlayer.rememberCard(table.getPileTopCard().get());

        while (gameInProgress()) {
            logCurrentScore();
            if (firstPlayer == humanPlayer) {
                playTurnHuman();
                if (gameOver()) break;
                playTurnBot();
            } else {
                playTurnBot();
                if (gameOver()) break;
                playTurnHuman();
            }
        }
        assignExtraPointsForMostGainedCards();
        determineWinner();
    }

    private void logCurrentScore() {
        System.out.println(scoreboard.getScores());
    }

    private void chooseFirstPlayer(Scanner scanner) {
        System.out.println("Who should go first? Enter '1' for Human or '2' for Bot.");
        String input = scanner.nextLine();
        while (!input.equals("1") && !input.equals("2")) {
            System.out.println("Invalid input. Please enter '1' for Human or '2' for Bot.");
            input = scanner.nextLine();
        }
        if (input.equals("1")) {
            firstPlayer = humanPlayer;
            System.out.println("Human player goes first.");
        } else {
            firstPlayer = botPlayer;
            System.out.println("Bot player goes first.");
        }
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
        considerStrategyChange();
        Card playedCard = botPlayer.playCard();

        resolveTurn(botPlayer, playedCard);
    }

    private void considerStrategyChange() {
        System.out.println("Considering strategy change. Current strategy: " + botPlayer.getStrategy());
        Map<Player, Integer> currentScores = scoreboard.getScores();
        int botScore = currentScores.get(botPlayer);
        int humanScore = currentScores.get(humanPlayer);
        int deckSize = dealer.deck().getCards().size();

        if (botScore < humanScore && deckSize <= Deck.TOTAL_CARDS / 2) {
            BotStrategy strategy = botPlayer.switchStrategy();
            System.out.println("Bot strategy changed to: " + strategy);
        }
    }

    private void resolveTurn(Player player, Card playedCard) {
        botPlayer.rememberCard(playedCard);
        botPlayer.logSeenCards();

        if (isTableEmpty()) {
            table.addCardFaceUp(playedCard);
        } else if (isMatchingCard(playedCard) || isJack(playedCard)) {
            checkAndHandlePisti(player, playedCard);
            table.addCardFaceUp(playedCard);
            player.addGainedCards(table.getCurrentPile());
            int pointsFromCards = player.getPointsFromCards(table.getCurrentPile());
            scoreboard.addScore(player, pointsFromCards);
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
            scoreboard.addPisti(player, PistiTypes.NORMAL_PISTI);
            System.out.println("***Pişti!*** Player took the pile and scored extra points!");
        } else if (table.getCurrentPile().size() == 1 && isJack(playedCard) && isJack(table.getPileTopCard().get())) {
            scoreboard.addScore(player, PISTI_WITH_JACK_SCORE);
            scoreboard.addPisti(player, PistiTypes.JACK_PISTI);
            System.out.println("***Pişti!*** Player took the pile with a Jack and scored extra points!");
        } else {
            System.out.println("Player took the pile without a pişti!");
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
