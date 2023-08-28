package com.example.cs2340Project;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BlackJackDeck {

    public int getCardRank(int card) {
        int cardValue = card % 13; // Get the value of the card (0-12)

        // Face cards (King, Queen, Jack) count as 10
        if (cardValue >= 10) {
            return 10;
        }
        // Ace has a value of 11
        else if (cardValue == 0) {
            return 11;
        }
        // All other cards have their face value
        else {
            return cardValue;
        }
    }

    /**
     * Deck of cards being played with.
     */
    private ArrayDeque<BlackJackCard> deck;

    /**
     * Number of decks to play with.
     */
    private int numDecks;

    /**
     * Number of cards from the deck that have currently been used.
     */
    private int cardsUsed;

    /**
     * Constructor that takes in the number of decks to work with.
     *
     * @param numDecks The number of decks to play with.
     */
    public BlackJackDeck(int numDecks) {
        this.numDecks = numDecks;
        this.deck = new ArrayDeque<BlackJackCard>();
        this.makeDeck();
        this.cardsUsed = 0;
    }

    /**
     * Constructor that defaults to working with 2 decks.
     */
    public BlackJackDeck() {
        this(2);
    }

    /**
     * Helper method to add all of the individual cards to the deck.
     */
    public void makeDeck() {
        char curSuite;
        for (int i = 0; i < 4; ++i) {
            for (int k = 2; k < 15; ++k) {
                for (int j = 0; j < numDecks; ++j) {
                    switch (i) {
                        case 0:
                            curSuite = 'S';
                            break;
                        case 1:
                            curSuite = 'C';
                            break;
                        case 2:
                            curSuite = 'H';
                            break;
                        default:
                            curSuite = 'D';
                    }
                    switch (k) {
                        case 11:
                            deck.addLast(new BlackJackCard(10, curSuite, "Jack"));
                            break;
                        case 12:
                            deck.addLast(new BlackJackCard(10, curSuite, "Queen"));
                            break;
                        case 13:
                            deck.addLast(new BlackJackCard(10, curSuite, "King"));
                            break;
                        case 14:
                            deck.addLast(new BlackJackCard(11, curSuite, "Ace"));
                            break;
                        default:
                            deck.addLast(new BlackJackCard(k, curSuite));
                    }
                }
            }
        }
        this.shuffle();
    }

    /**
     * Helper method to reshuffle whatever cards are in the deck.
     */
    public void shuffle() {
        BlackJackCard[] asArray = new BlackJackCard[deck.size()];
        int fullSize = deck.size();
        for (int i = 0; i < fullSize; ++i) {
            asArray[i] = deck.removeFirst();
        }
        ArrayList<BlackJackCard> shuffleArray = new ArrayList<>();
        shuffleArray.addAll(Arrays.asList(asArray));
        Random random = new Random();
        deck.clear();
        for (int i = 0; i < fullSize; ++i) {
            deck.addLast(shuffleArray.remove(random.nextInt(shuffleArray.size())));
        }
    }

    /**
     * Method to get the first card of the deck.
     *
     * @return The first card on the deck.
     */
    public BlackJackCard getCard() {
        cardsUsed++;
        return deck.removeFirst();
    }

    /**
     * Meant for use when a played is finished with their hand. Add the used cards to the
     * back of the deck.
     *
     * @param card
     */
    public void addCardToDeck(BlackJackCard card) {
        deck.addLast(card);
        if (cardsUsed >= .8*52*numDecks) {
            this.shuffle();
            cardsUsed = 0;
        }
    }
}
