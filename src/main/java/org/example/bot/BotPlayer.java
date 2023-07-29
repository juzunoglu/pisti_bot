package org.example.bot;

import org.example.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BotPlayer extends Player {

    private final Map<Value, Integer> seenCardsFrequency = new HashMap<>(52);
    private BotStrategy strategy;
    private final Table table;

    public BotPlayer(BotStrategy strategy, Table table) {
        super();
        this.strategy = strategy;
        this.table = table;
    }

    public BotStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(BotStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<Value, Integer> getSeenCardsFrequency() {
        return seenCardsFrequency;
    }

    public BotStrategy switchStrategy() {
        if (this.strategy instanceof OffensiveStrategy) {
            this.strategy = new UsualStrategy();
        } else {
            this.strategy = new OffensiveStrategy();
        }
        return this.strategy;
    }

    public void rememberCard(Card card) {
        seenCardsFrequency.put(card.getValue(), seenCardsFrequency.getOrDefault(card.getValue(), 0) + 1);
    }

    private Card chooseCard() {
        return strategy.chooseCard(this, table);
    }

    public void logSeenCards() {
        System.out.println("Bot memory : " + seenCardsFrequency);
    }

    public Optional<Card> getJackInHand() {
        return this.getHand().stream()
                .filter(card -> card.getValue() == Value.JACK)
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
