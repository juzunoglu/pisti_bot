package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

public class OffensiveStrategy extends CommonStrategy {
    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return chooseMatchingCard(bot, table)
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .orElseGet(() -> chooseRandomCard(bot, table));
    }

    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
