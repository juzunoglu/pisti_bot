package org.example.test;

import org.example.bot.BotPlayer;
import org.example.bot.OffensiveStrategy;
import org.example.bot.SafeStrategy;
import org.example.gameloop.Dealer;
import org.example.gameloop.Deck;
import org.example.gameloop.Game;
import org.example.gameloop.Scoreboard;
import org.example.gameloop.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.atomic.AtomicInteger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BotGameTest {

    private Deck deck;
    private Dealer dealer;
    private Table table;
    private BotPlayer bot1;
    private BotPlayer bot2;
    private Scoreboard scoreboard;
    private Game game;
    private AtomicInteger bot1Wins;
    private AtomicInteger bot2Wins;


    @BeforeAll
    public void setUp() {
        bot1Wins = new AtomicInteger(0);
        bot2Wins = new AtomicInteger(0);
    }

    @BeforeEach
    public void init() {
        deck = new Deck();
        dealer = new Dealer(deck);
        table = new Table();
        bot1 = new BotPlayer(new OffensiveStrategy(), table);
        bot2 = new BotPlayer(new SafeStrategy(), table);
        scoreboard = new Scoreboard(bot1, bot2);

        game = new Game(bot1, bot2, table, dealer, scoreboard);
    }

    @RepeatedTest(1000)
    public void testBotGame() {
        game.start();

        if (scoreboard.getScore(bot1) > scoreboard.getScore(bot2)) {
            bot1Wins.incrementAndGet();
        } else if (scoreboard.getScore(bot2) > scoreboard.getScore(bot1)) {
            bot2Wins.incrementAndGet();
        }
    }

    @AfterEach
    public void cleanup() {
        deck = null;
        dealer = null;
        table = null;
        bot1 = null;
        bot2 = null;
        scoreboard = null;
        game = null;
    }

    @AfterAll
    public void results() {
        System.out.println("After " + (bot1Wins.get() + bot2Wins.get()) + " games, Bot 1 (Offensive) won " + bot1Wins.get() + " games and Bot 2 (Safe) won " + bot2Wins.get() + " games.");
    }

}
