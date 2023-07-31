package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.Optional;

public class OffensiveStrategy extends CommonStrategy {

    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return chooseMatchingCard(bot, table)
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .orElseGet(() -> chooseRandomCard(bot, table));
    }

    @Override
    public Optional<Card> chooseJack(BotPlayer bot, Table table) {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
