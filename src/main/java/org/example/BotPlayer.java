package org.example;

import java.util.ArrayList;
import java.util.List;

public class BotPlayer extends Player {

    private final List<Card> seenCards = new ArrayList<>(52);

    public BotPlayer() {
        super();
    }

    public void rememberCard(Card card) {
        seenCards.add(card);
    }

    public List<Card> getSeenCards() {
        return seenCards;
    }

    // todo: always plays the first card.
    @Override
    public Card playCard() {
        logHand();
        Card selectedCard = this.getHand().get(1);
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
