package org.example.enums;

public enum Suit {
    CLUBS(0, "CLUB"),
    HEARTS(1, "HEARTS"),
    SPADES(2, "SPADES"),
    DIAMONDS(3, "DIAMONDS");
    private final int suitCode;
    private final String suitName;

    Suit(int suitCode, String suitName) {
        this.suitCode = suitCode;
        this.suitName = suitName;
    }

    public int getSuitCode() {
        return this.suitCode;
    }

    @Override
    public String toString() {
        return this.suitName;
    }
}
