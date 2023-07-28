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

    public void displayCurrentTable() {
        System.out.println("Current table state");
        if (faceUpCards.isEmpty()) {
            System.out.println("No face-up cards on table.");
        } else {
            System.out.println("Top card on table: " + faceUpCards.getLast());
        }
    }

    // Removes all cards (both face-up and face-down) from the table
    public List<Card> removeAllCards() {
        List<Card> allCards = new LinkedList<>(faceDownCards);
        allCards.addAll(faceUpCards);
        faceDownCards.clear();
        faceUpCards.clear();
        return allCards;
    }
}
