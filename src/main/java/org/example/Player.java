package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    protected List<Card> hand;
    protected List<Card> gained;
    protected int score;

    public Player() {
        this.hand = new LinkedList<>();
        this.gained = new LinkedList<>();
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

    public void addGainedCards(List<Card> cards) {
        gained.addAll(cards);
    }

    public void addPoints(int points) {
        score += points;
    }

    public abstract Card playCard();

    @Override
    public String toString() {
        return "Player{" +
                "hand=" + hand +
                ", gained=" + gained +
                ", score=" + score +
                '}';
    }
}
