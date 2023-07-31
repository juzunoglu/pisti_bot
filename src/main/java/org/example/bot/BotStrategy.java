package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.Optional;

public interface BotStrategy {
    Card chooseCard(BotPlayer bot, Table table);

    Optional<Card> chooseJack(BotPlayer bot, Table table);
}
