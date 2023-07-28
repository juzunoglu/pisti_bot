package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    protected List<Card> hand;
    protected int score;

    public Player() {
        this.hand = new LinkedList<>();
        this.score = 0;
    }

    public void addToHand(Card card) {
        this.hand.add(card);
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public boolean isHandEmpty() {
        return hand.isEmpty();
    }

    public void addPoints(int points) {
        score += points;
    }

    public abstract Card playCard();

}
