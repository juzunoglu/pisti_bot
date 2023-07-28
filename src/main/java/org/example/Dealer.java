package org.example;

public class Dealer {
    private final Deck deck;

    public Dealer() {
        this.deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    // Deals the initial cards to each player and the table
    public void dealInitialCards(Player player1, Player player2, Table table) {
        // Shuffle the deck before dealing
        deck.shuffle();

        // Deal 4 cards to each player
        for (int i = 0; i < 4; i++) {
            player1.addCard(deck.draw());
            player2.addCard(deck.draw());
        }

        // Place 3 cards face down on the table
        for (int i = 0; i < 3; i++) {
            table.addCardFaceDown(deck.draw());
        }

        // Place 1 card face up on the table
        table.addCardFaceUp(deck.draw());
    }

    // Deals 4 cards to a player if their hand is empty and the deck is not empty
    public void dealCardsToPlayer(Player player) {
        if (player.isHandEmpty() && !deck.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                player.addCard(deck.draw());
            }
        }
    }
}
