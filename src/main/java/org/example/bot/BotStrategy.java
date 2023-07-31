package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

public interface BotStrategy {
    Card chooseCard(BotPlayer bot, Table table);

    // todo: playJack should be moved here.

}
