package ru.nsu.tsydenov;

public enum Suit {
    CLUBS("Clubs"),
    DIAMONDS("Diamonds"),
    HEARTS("Hearts"),
    SPADES("Spades");

    private final String displayName;

    Suit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Suit fromIndex(int n) {
        return switch (n) {
            case 0 -> CLUBS;
            case 1 -> DIAMONDS;
            case 2 -> HEARTS;
            case 3 -> SPADES;
            default -> throw new IllegalArgumentException("invalid index suit");
        };
    }

    public int toIndex() {
        return switch (this) {
            case SPADES -> 0;
            case CLUBS -> 1;
            case HEARTS -> 2;
            case DIAMONDS -> 3;
        };
    }
}