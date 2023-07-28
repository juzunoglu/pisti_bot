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

    // todo: implement the best move here!
    @Override
    public Card playCard() {
        Card card = new Card(Suit.SPADES, Value.EIGHT);
        System.out.println("Bot player has played a card: " + card);
        return card;
    }
}
