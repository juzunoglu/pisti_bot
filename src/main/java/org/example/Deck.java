package org.example;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {

    private final LinkedList<Card> cards = new LinkedList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                cards.add(new Card(suit, value));
            }
        }
        shuffle();
    }

    public Card draw() {
        return cards.pop();
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
