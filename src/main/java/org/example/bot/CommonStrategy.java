package org.example.bot;

import org.example.Card;
import org.example.Table;

import java.util.Optional;

public abstract class CommonStrategy implements BotStrategy {

    protected Optional<Card> getMatchingCard(BotPlayer bot, Table table) {
        Optional<Card> topPileCard = table.getPileTopCard();
        if (topPileCard.isPresent()) {
            for (Card cardInHand : bot.getHand()) {
                if (cardInHand.getValue() == topPileCard.get().getValue()) {
                    return Optional.of(cardInHand);
                }
            }
        }
        return Optional.empty();
    }


    @Override
    public abstract Card chooseCard(BotPlayer bot, Table table);
}
