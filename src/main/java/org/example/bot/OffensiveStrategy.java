package org.example.bot;

import org.example.enums.Value;
import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.Map;
import java.util.Optional;

import static org.example.gameloop.Deck.TOTAL_CARDS;
import static org.example.gameloop.Deck.TOTAL_SAME_VALUE_CARDS;

public class OffensiveStrategy extends CommonStrategy {

    private static final double JACK_THRESHOLD = 0.1;

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
        if (bot.hasMoreThanOneJack() && !table.getCurrentPile().isEmpty()) {
            return bot.getJackInHand();
        } else if (!bot.hasMoreThanOneJack() && shouldPlayJack(bot, table)) {
            return bot.getJackInHand();
        }
        return Optional.empty();
    }


    private boolean shouldPlayJack(BotPlayer botPlayer, Table table) {

        if (table.getCurrentPile().isEmpty() && botPlayer.getHand().size() > 1)
            return false;

        Map<Value, Integer> seenCardsFrequency = botPlayer.getSeenCardsFrequency();
        double totalPistiProbability = botPlayer.getHand().stream()
                .mapToDouble(card -> calculatePistiProbability(seenCardsFrequency, card))
                .sum();

        return totalPistiProbability > JACK_THRESHOLD;
    }

    private double calculatePistiProbability(Map<Value, Integer> seenCardsFrequency, Card card) {

        int seenSameValueCards = seenCardsFrequency.getOrDefault(card.getValue(), 0);

        int unseenSameValueCards = TOTAL_SAME_VALUE_CARDS - seenSameValueCards;

        int unseenCards = TOTAL_CARDS - seenCardsFrequency
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return (double) unseenSameValueCards / unseenCards;
    }


    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
