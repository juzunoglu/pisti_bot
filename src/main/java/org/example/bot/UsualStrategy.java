package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.*;

public class UsualStrategy extends CommonStrategy {
    private static final int PILE_COUNT_THRESHOLD = 5;
    private static final int PILE_VALUE_THRESHOLD = 2;


    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        return this.chooseMatchingCard(bot, table)
                .or(() -> chooseJack(bot, table))
                .or(() -> chooseCardByFrequency(bot))
                .or(() -> chooseMostFrequentCardInHand(bot))
                .or(() -> chooseLeastValuableCard(bot))
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
        int pileSize = table.getCurrentPile().size();

        int pileValue = table.getCurrentPile().stream()
                .mapToInt(Card::calculateCardPoint)
                .sum();

        return pileSize > PILE_COUNT_THRESHOLD || pileValue > PILE_VALUE_THRESHOLD;
    }

    private Optional<Card> chooseLeastValuableCard(BotPlayer bot) {
        return bot.getHand().stream()
                .min(Comparator.comparingInt(Card::calculateCardPoint))
                .flatMap(minValuableCard -> {
                    boolean allSameValue = bot.getHand().stream()
                            .allMatch(card -> card.calculateCardPoint() == minValuableCard.calculateCardPoint());
                    if (!allSameValue) {
                        System.out.println("Bot has chosen the least valuable card in hand");
                        return Optional.of(minValuableCard);
                    }
                    return Optional.empty();
                });
    }

    @Override
    public String toString() {
        return "UsualStrategy";
    }
}
