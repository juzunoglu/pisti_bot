package org.example.bot;

import org.example.enums.Value;
import org.example.gameloop.Card;
import org.example.gameloop.Table;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class OffensiveStrategy extends CommonStrategy {

    private static final double JACK_THRESHOLD = 15.0;

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
        if (shouldPlayJack(bot, table)) {
            return bot.getJackInHand();
        }
        return Optional.empty();
    }

    private double calculatePistiProbability(Card card, Map<Value, Integer> cardFrequency, double unseenCardNumber) {
        double probability = 0.0;
        int seenCardSize = 3;
        if (Objects.nonNull(cardFrequency.get(card.getValue()))) {
            seenCardSize = 3 - cardFrequency.get(card.getValue());
        }
        if (seenCardSize >= 1.0) {
            probability = (((double) seenCardSize / unseenCardNumber) * 100.0);
            System.out.println("Bot is trying to decide whether a Jack should be played or not");
            System.out.println("The card for which we are trying to calculate: " + card.getValue());
            System.out.println("The total number unseen cards is " + unseenCardNumber);
            System.out.println("Calculated probability is: " + probability);
        }
        return probability;
    }

    private boolean shouldPlayJack(BotPlayer bot, Table table) {
        double probabilitySum = 0.0;
        double combinedProbabilityResult = 0.0;
        double unseenCardNum = 52 - (table.getFaceDownCards().size() + table.getFaceUpCards().size());
        List<Card> handWithoutJack = bot.getHand()
                .stream()
                .filter(this::excludeJack)
                .toList();

        for (Card card : handWithoutJack) {
            probabilitySum += calculatePistiProbability(card, bot.getSeenCardsFrequency(), unseenCardNum);
        }

        combinedProbabilityResult = probabilitySum / (double) handWithoutJack.size();
        return combinedProbabilityResult > JACK_THRESHOLD;
    }


    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
