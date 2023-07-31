package org.example.bot;

import org.example.Card;
import org.example.Table;
import org.example.Value;

import java.util.*;
import java.util.stream.IntStream;

public class UsualStrategy extends CommonStrategy {
    private static final int PILE_THRESHOLD = 5;

    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return getMatchingCard(bot, table)
                .or(() -> playJackIfAppropriate(bot, table))
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .orElseGet(() -> playRandomCard(bot, table));
    }

    private Optional<Card> playJackIfAppropriate(BotPlayer bot, Table table) {
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
