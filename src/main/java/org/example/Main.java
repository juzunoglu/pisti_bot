package org.example;

import org.example.bot.BotPlayer;
import org.example.bot.UsualStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = initializeGameState();

        game.start();
    }

    private static Game initializeGameState() {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        var human = new HumanPlayer(scanner);
        Table table = new Table();
        Scoreboard scoreboard = new Scoreboard();
        var bot = new BotPlayer(new UsualStrategy(), table, scoreboard);

        Dealer dealer = new Dealer(deck);
        return new Game(human, bot, dealer, table, scoreboard);
    }
}