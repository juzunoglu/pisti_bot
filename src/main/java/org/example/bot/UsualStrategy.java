package org.example.bot;

import org.example.Card;
import org.example.Table;
import org.example.Value;

import java.util.*;
import java.util.stream.IntStream;

public class UsualStrategy extends CommonStrategy {

    private static final int PILE_THRESHOLD = 2;

    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return getMatchingCard(bot, table)
                .or(() -> playJackIfAppropriate(bot, table))
                .or(() -> chooseCardByFrequency(bot))
                .orElseGet(() -> playRandomCard(bot));
    }

    private Optional<Card> playJackIfAppropriate(BotPlayer bot, Table table) {
        if (shouldPlayJack(table) && bot.getHand().size() > 1) {
            return bot.getJackInHand();
        }
        return Optional.empty();
    }

    private Optional<Card> chooseCardByFrequency(BotPlayer bot) {
        Map<Value, Integer> seenCardsFrequency = bot.getSeenCardsFrequency();
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .sorted(Collections.reverseOrder())
                .flatMap(i -> bot.getHand().stream()
                        .filter(card -> !isCardJack(card)) // Do not consider "J" in this strategy
                        .filter(card -> Objects.equals(seenCardsFrequency.getOrDefault(card.getValue(), 0), i)))
                .findFirst();
    }

    private Card playRandomCard(BotPlayer bot) {
        return bot.getHand().get(new Random().nextInt(bot.getHand().size()));
    }


    private boolean shouldPlayJack(Table table) {
        return table.getCurrentPile().size() > PILE_THRESHOLD;
    }

    private boolean isCardJack(Card card) {
        return card.getValue() == Value.JACK;
    }

    @Override
    public String toString() {
        return "UsualStrategy";
    }
}
