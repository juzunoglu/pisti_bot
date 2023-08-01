package org.example.bot;

import org.example.enums.Value;
import org.example.gameloop.Card;
import org.example.gameloop.Table;
import org.example.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class BotPlayer extends Player {

    private final Map<Value, Integer> seenCardsFrequency = new HashMap<>(52);
    private BotStrategy strategy;
    private final Table table;
    private boolean hasChangedStrategy = false;


    public BotPlayer(BotStrategy strategy, Table table) {
        super();
        this.strategy = strategy;
        this.table = table;
    }

    public boolean isHasChangedStrategy() {
        return hasChangedStrategy;
    }

    public BotStrategy getStrategy() {
        return strategy;
    }

    public Map<Value, Integer> getSeenCardsFrequency() {
        return seenCardsFrequency;
    }

    public BotStrategy switchStrategy() {
        if (!hasChangedStrategy && strategy instanceof OffensiveStrategy) {
            strategy = new UsualStrategy();
            hasChangedStrategy = true;
        } else if (!hasChangedStrategy) {
            strategy = new OffensiveStrategy();
            hasChangedStrategy = true;
        }
        return strategy;
    }

    public void rememberCard(Card card) {
        seenCardsFrequency.put(card.getValue(), seenCardsFrequency.getOrDefault(card.getValue(), 0) + 1);
    }

    public void rememberCards(LinkedList<Card> cards) {
        for (Card card : cards) {
            rememberCard(card);
        }
    }

    public boolean hasMoreThanOneJack() {
        return getHand().stream()
                .filter(jack())
                .count() > 1;
    }

    private static Predicate<Card> jack() {
        return card -> card.getValue() == Value.JACK;
    }

    private Card chooseCard() {
        return strategy.chooseCard(this, table);
    }

    public void logSeenCards() {
        System.out.println("Bot memory : " + seenCardsFrequency);
    }

    public Optional<Card> getJackInHand() {
        return this.getHand().stream()
                .filter(jack())
                .peek(it -> System.out.println("Bot played jack decisively"))
                .findFirst();
    }

    @Override
    public Card playCard() {
        logHand();
        Card selectedCard = this.chooseCard();
        System.out.println("Bot move: " + selectedCard);
        getHand().remove(selectedCard);
        return selectedCard;
    }

    @Override
    public void logHand() {
        System.out.println("Bots hand: ");
        for (int i = 0; i < this.getHand().size(); i++) {
            System.out.println((i + 1) + ": " + this.getHand().get(i));
        }
    }

    @Override
    public String toString() {
        return "BotPlayer{" +
                "gainedCards=" + gainedCards +
                '}';
    }
}
