package org.example.player;

import org.example.gameloop.Card;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    protected String name;
    protected List<Card> hand;
    protected List<Card> gainedCards;
    public Player() {
        this.hand = new LinkedList<>();
        this.gainedCards = new LinkedList<>();
    }

    public List<Card> getGainedCards() {
        return this.gainedCards;
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
        gainedCards.addAll(cards);
    }


    public int getPointsFromCards(List<Card> gainedCards) {
        return gainedCards.stream()
                .mapToInt(Card::getPoints)
                .sum();
    }

    public abstract void logHand();

    public abstract Card playCard();
}
