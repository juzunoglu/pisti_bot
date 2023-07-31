package org.example.gameloop;

import org.example.enums.Suit;
import org.example.enums.Value;

public class Card {

    private final Suit suit;
    private final Value value;
    private final int points;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
        this.points = calculateCardPoint(suit, value);
    }

    private int calculateCardPoint(Suit suit, Value value) {
        if (suit == Suit.DIAMONDS && value == Value.TEN) {
            return 3;
        } else if (suit == Suit.CLUBS && value == Value.TWO) {
            return 2;
        } else if (value == Value.ACE || value == Value.JACK) {
            return 1;
        } else {
            return 0;
        }
    }


    public Value getValue() {
        return this.value;
    }

    public int getPoints() {
        return this.points;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }


}
