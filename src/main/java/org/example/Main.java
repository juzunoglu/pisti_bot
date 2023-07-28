package org.example;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Player human = new Player();
        Player bot = new Player();
        Table table = new Table();
        Scanner scanner = new Scanner(System.in);

        Dealer dealer = new Dealer(deck);
        Game game = new Game(human, bot, dealer, table, scanner);

        game.start();
    }
}