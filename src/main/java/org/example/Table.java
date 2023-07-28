package org.example;

import java.util.LinkedList;
import java.util.List;

public class Table {
    private final LinkedList<Card> faceDownCards = new LinkedList<>();
    private final LinkedList<Card> faceUpCards = new LinkedList<>();

    // Adds a card to the pile of face-down cards on the table
    public void addCardFaceDown(Card card) {
        faceDownCards.add(card);
    }

    // Adds a card to the pile of face-up cards on the table
    public void addCardFaceUp(Card card) {
        faceUpCards.add(card);
    }

    public void display() {
        if (!faceUpCards.isEmpty()) {
            System.out.println("Top card on table: " + faceUpCards.peek());
        } else {
            System.out.println("No face-up cards on table.");
        }
    }

    // Returns the last face-up card on the table, or null if there are no face-up cards
    public Card getLastFaceUpCard() {
        return faceUpCards.isEmpty() ? null : faceUpCards.getLast();
    }

    // Removes all cards (both face-up and face-down) from the table
    public List<Card> removeAllCards() {
        List<Card> allCards = new LinkedList<>(faceDownCards);
        allCards.addAll(faceUpCards);
        faceDownCards.clear();
        faceUpCards.clear();
        return allCards;
    }

    // Returns the number of face-up cards on the table
    public int countFaceUpCards() {
        return faceUpCards.size();
    }
}
