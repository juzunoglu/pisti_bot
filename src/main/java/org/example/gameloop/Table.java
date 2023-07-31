package org.example.gameloop;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Table {
    private final LinkedList<Card> faceDownCards = new LinkedList<>();
    private final LinkedList<Card> faceUpCards = new LinkedList<>();

    public LinkedList<Card> getFaceUpCards() {
        return faceUpCards;
    }

    public LinkedList<Card> getFaceDownCards() {
        return faceDownCards;
    }

    public void addCardFaceDown(Card card) {
        faceDownCards.add(card);
    }

    public void addCardFaceUp(Card card) {
        faceUpCards.add(card);
    }

    public void displayCurrentTable() {
        System.out.println("Current table state: " + this.getCurrentPile());
        if (faceUpCards.isEmpty()) {
            System.out.println("No face-up cards on table.");
        } else {
            System.out.println("Top card on table: " + getPileTopCard());
        }
    }

    public Optional<Card> getPileTopCard() {
        return faceUpCards.isEmpty() ? Optional.empty() : Optional.of(faceUpCards.getLast());
    }

    public void removeAllCards() {
        faceDownCards.clear();
        faceUpCards.clear();
    }

    public List<Card> getCurrentPile() {
        List<Card> allCards = new LinkedList<>(faceDownCards);
        allCards.addAll(faceUpCards);
        return allCards;
    }
}
