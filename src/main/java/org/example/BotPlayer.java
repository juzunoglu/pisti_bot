package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BotPlayer extends Player {

    private final Map<Value, Integer> seenCardsFrequency = new HashMap<>(52);

    public BotPlayer() {
        super();
    }

    public void rememberCard(Card card) {
        seenCardsFrequency.put(card.getValue(), seenCardsFrequency.getOrDefault(card.getValue(), 0) + 1);
    }

    public void logSeenCards() {
        System.out.println("Bot memory : " + seenCardsFrequency);
    }

    @Override
    public Card playCard() {
        logHand();

        // Look for a card in the hand that has been seen 3, 2, or 1 times.
        Card selectedCard = null;
        for (int i = 3; i > 0; i--) {
            for (Card cardInHand : this.getHand()) {
                if (seenCardsFrequency.getOrDefault(cardInHand.getValue(), 0) == i) {
                    selectedCard = cardInHand;
                    break;
                }
            }
            if (selectedCard != null) {
                break;
            }
        }

        // If no card has been seen 3, 2, or 1 times, play a random card.
        if (selectedCard == null) {
            selectedCard = this.getHand().get(new Random().nextInt(this.getHand().size()));
        }

        System.out.println("Bot move: " + selectedCard);
        this.getHand().remove(selectedCard);
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
