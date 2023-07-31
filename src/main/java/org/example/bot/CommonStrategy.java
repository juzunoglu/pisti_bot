package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;
import org.example.enums.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class CommonStrategy implements BotStrategy {

    protected Optional<Card> getMatchingCard(BotPlayer bot, Table table) {
        Optional<Card> topPileCard = table.getPileTopCard();
        if (topPileCard.isPresent()) {
            for (Card cardInHand : bot.getHand()) {
                if (cardInHand.getValue() == topPileCard.get().getValue()) {
                    System.out.println("Bot has played a matching card");
                    return Optional.of(cardInHand);
                }
            }
        }
        return Optional.empty();
    }

    protected Optional<Card> chooseMostFrequentCardInHand(BotPlayer bot) {
        Map<Value, Long> cardFrequencies = bot.getHand().stream()
                .filter(card -> card.getValue() != Value.JACK) // Exclude JACK cards from the frequency map
                .collect(Collectors.groupingBy(Card::getValue, Collectors.counting()));

        return cardFrequencies.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .flatMap(maxEntry -> bot.getHand().stream()
                        .filter(card -> card.getValue() == maxEntry.getKey())
                        .peek(it -> System.out.println("Bot choose the card in its hand frequency map"))
                        .findFirst());
    }

    protected Optional<Card> chooseCardByFrequency(BotPlayer bot) {
        Map<Value, Integer> seenCardsFrequency = bot.getSeenCardsFrequency();
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .sorted(Collections.reverseOrder())
                .flatMap(i -> bot.getHand().stream()
                        .filter(card -> !isCardJack(card)) // Do not consider "J" in this strategy
                        .filter(card -> Objects.equals(seenCardsFrequency.getOrDefault(card.getValue(), 0), i)))
                .peek(it -> System.out.println("Bot has played the card based on the frequency map"))
                .findFirst();
    }

    public boolean isCardJack(Card card) {
        return card.getValue() == Value.JACK;
    }

    protected Card playRandomCard(BotPlayer bot, Table table) {
        System.out.println("Bot played card randomly");
        List<Card> handWithoutJack = bot.getHand().stream().filter(card -> !isCardJack(card)).toList();
        if (table.getCurrentPile().isEmpty() && !handWithoutJack.isEmpty()) {
            // If the table is empty and there are other cards besides Jack, pick one of them randomly
            return handWithoutJack.get(new Random().nextInt(handWithoutJack.size()));
        }
        // Otherwise, pick any card randomly
        return bot.getHand().get(new Random().nextInt(bot.getHand().size()));
    }


    @Override
    public abstract Card chooseCard(BotPlayer bot, Table table);
}
