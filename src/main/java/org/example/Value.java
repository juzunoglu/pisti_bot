package org.example;

public enum Value {
    ACE(0, "ACE"),
    TWO(1, "2"),
    THREE(2, "3"),
    FOUR(3, "4"),
    FIVE(4, "5"),
    SIX(5, "6"),
    SEVEN(6, "7"),
    EIGHT(7, "8"),
    NINE(8, "9"),
    TEN(9, "10"),
    JACK(10, "JACK"),
    QUEEN(11, "QUEEN"),
    KING(12, "KING");

    private final int valueCode;
    private final String valueName;

    Value(int valueCode, String valueName) {
        this.valueCode = valueCode;
        this.valueName = valueName;
    }

    public int getValueCode() {
        return this.valueCode;
    }

    @Override
    public String toString() {
        return this.valueName;
    }
}