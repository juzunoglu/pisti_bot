package org.example;

public class BotPlayer extends Player {

    public BotPlayer() {
        super();
    }

    // todo: implement the best move here!
    @Override
    public Card playCard() {
        Card card = new Card(Suit.SPADES, Value.EIGHT);
        System.out.println("Bot player has played a card: " + card);
        return card;
    }
}
