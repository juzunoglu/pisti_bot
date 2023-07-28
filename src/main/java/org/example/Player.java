package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    protected List<Card> hand;
    protected List<Card> gainedCards;
    protected int score;

    public Player() {
        this.hand = new LinkedList<>();
        this.gainedCards = new LinkedList<>();
        this.score = 0;
    }

    public List<Card> getGainedCards() {
        return this.gainedCards;
    }

    public int getScore() {
        return score;
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

    public List<Card> addGainedCards(List<Card> cards) {
        gainedCards.addAll(cards);
        return gainedCards;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void addPoints(List<Card> gainedCards) {
        int points = gainedCards.stream()
                .mapToInt(Card::getPoints)
                .sum();

        this.score += points;
    }

    public abstract Card playCard();

    @Override
    public String toString() {
        return "Player{" +
                "hand=" + hand +
                ", gained=" + gainedCards +
                ", score=" + score +
                '}';
    }
}
