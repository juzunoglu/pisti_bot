package org.example.bot;

import org.example.Card;
import org.example.Table;
import org.example.Value;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class UsualStrategy extends CommonStrategy {

    @Override
    public Card chooseCard(BotPlayer bot, Table table) {
        Optional<Card> matchingCard = this.getMatchingCard(bot, table);
        if (matchingCard.isPresent()) {
            return matchingCard.get();
        }

        // If pile's size is greater than 2 and bot has "J", play "J"
        if (table.getCurrentPile().size() > 2 && bot.isJInHand()) {
            for (Card cardInHand : bot.getHand()) {
                if (cardInHand.getValue() == Value.JACK) {
                    return cardInHand;
                }
            }
        }

        // If no matching card, use seen card frequency strategy
        Map<Value, Integer> seenCardsFrequency = bot.getSeenCardsFrequency();
        for (int i = 3; i > 0; i--) {
            for (Card cardInHand : bot.getHand()) {
                if (seenCardsFrequency.getOrDefault(cardInHand.getValue(), 0) == i) {
                    return cardInHand;
                }
            }
        }

        // If no card has been seen 3, 2, or 1 times and no J in hand, play a random card
        return bot.getHand().get(new Random().nextInt(bot.getHand().size()));
    }
}
