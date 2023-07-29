package org.example.bot;

import org.example.Card;
import org.example.Player;
import org.example.Table;
import org.example.Value;

import java.util.HashMap;
import java.util.Map;

public class BotPlayer extends Player {

    private final Map<Value, Integer> seenCardsFrequency = new HashMap<>(52);
    private BotStrategy strategy;
    private final Table table;

    public BotPlayer(BotStrategy strategy, Table table) {
        super();
        this.strategy = strategy;
        this.table = table;

    }

    public Map<Value, Integer> getSeenCardsFrequency() {
        return seenCardsFrequency;
    }

    public void switchStrategy() {
        if (this.strategy instanceof OffensiveStrategy) {
            this.strategy = new UsualStrategy();
        } else {
            this.strategy = new OffensiveStrategy();
        }
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

    public boolean isJInHand() {
        for (Card cardInHand : getHand()) {
            if (cardInHand.getValue() == Value.JACK) {
                return true;
            }
        }
        return false;
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
}
