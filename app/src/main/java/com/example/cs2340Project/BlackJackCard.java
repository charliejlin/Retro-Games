package com.example.cs2340Project;

public class BlackJackCard {
    private int value;
    private char suit;
    private String type;

    /**
     * Constructor for a single card
     *
     * @param value Card's value
     * @param suit Suit of the card (spaces, clubs, etc.)
     * @param type Type of card (for faces)
     */
    public BlackJackCard(int value, char suit, String type) {
        this.suit = suit;
        this.value = value;
        this.type = type;
    }

    public BlackJackCard(int value, char suit) {
        this(value, suit, null);
    }

    /**
     * Setter for value of card
     *
     * @param value Card's value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter for card's value
     *
     * @return Card's value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter for card's suit.
     *
     * @param suit Suit of card
     */
    public void setSuit(char suit) {
        this.suit = suit;
    }

    /**
     * Getter for card's suit
     *
     * @return Card's suit
     */
    public char getSuit() {
        return this.suit;
    }

    /**
     * Setter for the card's type
     *
     * @param type Type of card (for face cards)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the card's type
     *
     * @return Card's type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Return the String representation of a card.
     *
     * @return String representation of a card
     */
    @Override
    public String toString() {
        String cardSuit;
        switch (suit) {
            case 'S':
                cardSuit = "Spades";
                break;
            case 'D':
                cardSuit = "Diamonds";
                break;
            case 'H':
                cardSuit = "Hearts";
                break;
            default:
                cardSuit = "Clubs";
                break;
        }
        if (type == null) {
            return Integer.toString(value) + " of " + cardSuit;
        } else {
            return type + " of " + cardSuit;
        }
    }
}
