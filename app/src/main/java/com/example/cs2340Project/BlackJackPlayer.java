package com.example.cs2340Project;

import java.util.ArrayList;

public class BlackJackPlayer {
    private static BlackJackPlayer instance;
    private ArrayList<BlackJackCard> hand;
    private int lives;
    private int score;



    public static BlackJackPlayer getInstance() {
        if (instance == null) {
            instance = new BlackJackPlayer();
        }
        return instance;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void reduceLives() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void incrementScore() {
        score++;
    }


    /**
     * Constructor for a player object.
     */
    public BlackJackPlayer() {

        hand = new ArrayList<>();
        lives = 0;
        score = 0;
        hand = new ArrayList<>();
    }

    /**
     * Add a card to current hand.
     *
     * @param deck
     */
    public void hit(BlackJackDeck deck) {
        hand.add(deck.getCard());
    }

    /**
     * Method to clear the player's hand.
     *
     * @param deck Deck of cards being played with.
     */
    public void clearHand(BlackJackDeck deck) {
        int fullSize = hand.size();
        for (int i = 0; i < fullSize; ++i) {
            deck.addCardToDeck(hand.remove(0));
        }
    }

    /**
     * Gets the current sum of a player's hand.
     *
     * @return The sum of player's hand.
     */
    public int getHandSum() {
        int sum = 0;
        for (BlackJackCard card: hand) {
            sum += card.getValue();
        }
        return sum;
    }

    /**
     * Returns the player's hand.
     *
     * @return The hand.
     */
    public ArrayList<BlackJackCard> getHand() {
        return this.hand;
    }
}
