package org.example.bot;

import org.example.Card;
import org.example.Table;

public class OffensiveStrategy extends CommonStrategy {
    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return getMatchingCard(bot, table)
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .orElseGet(() -> playRandomCard(bot, table));
    }

    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
