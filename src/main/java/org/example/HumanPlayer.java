package org.example;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner scanner;

    public HumanPlayer(Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    @Override
    public void logHand() {
        System.out.println("Your hand: ");
        for (int i = 0; i < this.getHand().size(); i++) {
            System.out.println((i + 1) + ": " + this.getHand().get(i));
        }
    }

    @Override
    public Card playCard() {
        Card selectedCard = getPlayerInput();
        if (getHand().contains(selectedCard)) {
            getHand().remove(selectedCard);
            return selectedCard;
        } else {
            System.out.println("You can't play a card that's not in your hand.");
            return playCard();
        }
    }

    private Card getPlayerInput() {
        logHand();

        System.out.println("Enter the number corresponding to the card you want to play:");
        int cardIndex = scanner.nextInt() - 1;

        if (cardIndex < 0 || cardIndex >= getHand().size()) {
            System.out.println("Invalid selection. Please enter a number between 1 and " + hand.size());
            return getPlayerInput();
        }

        return getHand().get(cardIndex);
    }

    @Override
    public String toString() {
        return "HumanPlayer{" +
                "gainedCards=" + gainedCards +
                '}';
    }
}
