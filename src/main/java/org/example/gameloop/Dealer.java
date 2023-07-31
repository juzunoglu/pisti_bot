package org.example.gameloop;

import org.example.bot.BotPlayer;
import org.example.player.HumanPlayer;
import org.example.player.Player;

public record Dealer(Deck deck) {

    public void dealInitialCards(HumanPlayer humanPlayer, BotPlayer botPlayer, Table table) {
        deck.shuffle();

        for (int i = 0; i < 4; i++) {
            humanPlayer.addToHand(deck.draw());
            botPlayer.addToHand(deck.draw());
        }

        for (int i = 0; i < 3; i++) {
            table.addCardFaceDown(deck.draw());
        }

        table.addCardFaceUp(deck.draw());
    }

    public void dealCardsToPlayer(Player player) {
        if (player.isHandEmpty() && !deck.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                player.addToHand(deck.draw());
            }
        }
    }

    public void logDeckSize() {
        System.out.println("Remaining deck size: " + this.deck.getCards().size());
    }
}
