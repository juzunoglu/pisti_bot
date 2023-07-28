package org.example;

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
        var bot = new BotPlayer();
        Table table = new Table();

        Dealer dealer = new Dealer(deck);
        return new Game(human, bot, dealer, table);
    }
}