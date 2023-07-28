package org.example;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private final LinkedList<Card> hand = new LinkedList<>();
    private int score = 0;

    // Adds a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }

    public void displayHand() {
        for (Card card : this.getHand()) {
            System.out.println(card.toString());
        }
    }

    // Plays a card from the player's hand
    // This method removes and returns the first card in the player's hand
    // If the player has no cards left, it returns null
    public Card playCard(Card card) {
        if (!hand.contains(card)) {
            throw new IllegalArgumentException("Card not in hand");
        }
        hand.remove(card);
        return card;
    }

    // Increases the player's score by a certain amount
    public void addPoints(int points) {
        score += points;
    }

    // Returns the player's current score
    public int getScore() {
        return score;
    }

    // Returns a list of the cards currently in the player's hand
    public List<Card> getHand() {
        return List.copyOf(hand);
    }

    // Checks if the player's hand is empty
    public boolean isHandEmpty() {
        return hand.isEmpty();
    }
}
