package org.example.gameloop;

import org.example.enums.Suit;
import org.example.enums.Value;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {

    public static final int TOTAL_CARDS = 52;
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
