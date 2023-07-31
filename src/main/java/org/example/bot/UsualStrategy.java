package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.*;

public class UsualStrategy extends CommonStrategy {
    private static final int PILE_THRESHOLD = 5;

    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return this.chooseMatchingCard(bot, table)
                .or(() -> chooseJack(bot, table))
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .orElseGet(() -> chooseRandomCard(bot, table));
    }

    @Override
    public Optional<Card> chooseJack(BotPlayer bot, Table table) {
        if (shouldPlayJack(table)) {
            return bot.getJackInHand();
        }
        return Optional.empty();
    }


    private boolean shouldPlayJack(Table table) {
        return table.getCurrentPile().size() > PILE_THRESHOLD;
    }

    @Override
    public String toString() {
        return "UsualStrategy";
    }
}
