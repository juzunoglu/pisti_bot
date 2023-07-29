package org.example.bot;

import org.example.Card;
import org.example.Table;

public interface BotStrategy {
    Card chooseCard(BotPlayer bot, Table table);

}
