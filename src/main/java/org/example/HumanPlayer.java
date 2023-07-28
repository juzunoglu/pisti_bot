package org.example;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner scanner;

    public HumanPlayer(Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    @Override
    public Card playCard() {
        Card selectedCard = getPlayerInput();
        if (hand.contains(selectedCard)) {
            hand.remove(selectedCard);
            return selectedCard;
        } else {
            System.out.println("You can't play a card that's not in your hand.");
            return playCard();
        }
    }

    private Card getPlayerInput() {
        System.out.println("Your hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }

        System.out.println("Enter the number corresponding to the card you want to play:");
        int cardIndex = scanner.nextInt() - 1;

        if (cardIndex < 0 || cardIndex >= hand.size()) {
            System.out.println("Invalid selection. Please enter a number between 1 and " + hand.size());
            return getPlayerInput();
        }

        return hand.get(cardIndex);
    }
}
