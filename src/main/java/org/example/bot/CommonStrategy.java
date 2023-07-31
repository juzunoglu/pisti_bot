package org.example.bot;

import org.example.gameloop.Card;
import org.example.gameloop.Table;
import org.example.enums.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class CommonStrategy implements BotStrategy {

    @Override
    public abstract Optional<Card> chooseJack(BotPlayer bot, Table table);

    @Override
    public abstract Card chooseCard(BotPlayer bot, Table table);


    protected Optional<Card> chooseMatchingCard(BotPlayer bot, Table table) {
        Optional<Card> topPileCard = table.getPileTopCard();
        return topPileCard.flatMap(
                card -> bot.getHand().stream()
                        .filter(cardInHand -> cardInHand.getValue() == card.getValue())
                        .peek(c -> System.out.println("Bot has played a matching card"))
                        .findFirst());
    }

    protected Optional<Card> chooseMostFrequentCardInHand(BotPlayer bot) {
        Map<Value, Long> cardFrequencies = bot.getHand().stream()
                .filter(this::excludeJack)
                .collect(Collectors.groupingBy(Card::getValue, Collectors.counting()));

        Optional<Map.Entry<Value, Long>> maxEntryOpt = cardFrequencies.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (maxEntryOpt.isPresent()) {
            long maxFrequency = maxEntryOpt.get().getValue();
            boolean allSameFrequency = cardFrequencies.values().stream()
                    .allMatch(freq -> freq == maxFrequency);

            if (!allSameFrequency) {
                Value maxValue = maxEntryOpt.get().getKey();
                return bot.getHand().stream()
                        .filter(card -> card.getValue() == maxValue)
                        .peek(it -> System.out.println("Bot choose the card in its hand frequency map"))
                        .findFirst();
            }
        }

        return Optional.empty();
    }


    protected Optional<Card> chooseCardByFrequency(BotPlayer bot) {
        Map<Value, Integer> seenCardsFrequency = bot.getSeenCardsFrequency();
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .sorted(Collections.reverseOrder())
                .flatMap(i -> bot.getHand().stream()
                        .filter(this::excludeJack) // Do not consider "J" in this strategy
                        .filter(card -> Objects.equals(seenCardsFrequency.getOrDefault(card.getValue(), 0), i)))
                .peek(it -> System.out.println("Bot has played the card based on the frequency map"))
                .findFirst();
    }

    protected boolean excludeJack(Card card) {
        return card.getValue() != Value.JACK;
    }

    protected Card chooseRandomCard(BotPlayer bot, Table table) {
        System.out.println("Bot played card randomly");
        List<Card> handWithoutJack = bot.getHand()
                .stream()
                .filter(this::excludeJack)
                .toList();
        if (table.getCurrentPile().isEmpty() && !handWithoutJack.isEmpty()) {
            // If the table is empty and there are other cards besides Jack, pick one of them randomly
            return handWithoutJack.get(new Random().nextInt(handWithoutJack.size()));
        }
        // Otherwise, pick any card randomly
        return bot.getHand().get(new Random().nextInt(bot.getHand().size()));
    }
}
