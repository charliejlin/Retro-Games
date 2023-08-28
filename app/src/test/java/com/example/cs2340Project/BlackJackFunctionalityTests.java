package com.example.cs2340Project;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.*;

public class BlackJackFunctionalityTests {

    @Test
    public void testDeckCreation() {
        BlackJackDeck myDeck = new BlackJackDeck();
        assertEquals(myDeck, new BlackJackDeck());
    }

    @Test
    public void testShuffle() {
        BlackJackDeck myDeck = new BlackJackDeck();
        myDeck.makeDeck();
        BlackJackDeck testDeck = myDeck;
        myDeck.shuffle();
        assertNotEquals(testDeck.getCard(), myDeck.getCard());
    }

    @Test
    public void testCardToString() {
        BlackJackCard card1 = new BlackJackCard(8, 'H');
        BlackJackCard card2 = new BlackJackCard(10, 'S', "Jack");
        BlackJackCard card3 = new BlackJackCard(8, 'H');

        String test = card1.toString();
        String test2 = card2.toString();
        String test3 = card3.toString();

        assertEquals("8 of Hearts", test);
        assertEquals("Jack of Spades", test2);
        assertEquals(test, test3);
        assertNotEquals(test, test2);
    }

    @Test
    public void testClearHand() {
        BlackJackDeck myDeck = new BlackJackDeck();
        BlackJackPlayer me = new BlackJackPlayer();
        BlackJackDealer dealer = new BlackJackDealer();

        for (int i = 0; i < 4; i++) {
            me.hit(myDeck);
            dealer.hit(myDeck);
        }

        me.clearHand(myDeck);
        dealer.clearHand(myDeck);

        assertEquals(me.getHand().size(), 0);
        assertEquals(dealer.getHand().size(), 0);
    }
    @Test
    public void testHitDealerHand() {
        BlackJackDeck myDeck = new BlackJackDeck();
        BlackJackDealer dealer = new BlackJackDealer();

        myDeck.makeDeck();
        dealer.hit(myDeck);
        ArrayList<BlackJackCard> test = dealer.getHand();

        dealer.dealerHit(myDeck);

        test = dealer.getHand();
        assertEquals(test.size(), 2);
    }


    @Test
    public void testPlayerHandSum() {
        BlackJackDeck deck = new BlackJackDeck();
        deck.makeDeck();
        BlackJackPlayer player = new BlackJackPlayer();

        player.hit(deck);
        player.hit(deck);

        ArrayList<BlackJackCard> hand = player.getHand();

        hand.get(0).setValue(5);
        hand.get(1).setValue(10);

        assertEquals(15,player.getHandSum());
    }

    @Test
    public void testPlayerWin() {
        //player win
        BlackJackDeck deck = new BlackJackDeck();
        deck.makeDeck();

        BlackJackPlayer player = new BlackJackPlayer();
        BlackJackDealer dealer = new BlackJackDealer();

        player.hit(deck);
        dealer.hit(deck);
        player.hit(deck);
        dealer.hit(deck);

        ArrayList<BlackJackCard> playerHand = player.getHand();
        ArrayList<BlackJackCard> dealerHand = dealer.getHand();

        playerHand.get(0).setValue(10);
        playerHand.get(1).setValue(10);

        dealerHand.get(0).setValue(10);
        dealerHand.get(1).setValue(5);

        assertEquals(dealer.playerWin(player), 2);
    }

    @Test
    public void testGetHand() {
        BlackJackPlayer player = new BlackJackPlayer();
        ArrayList<BlackJackCard> test = new ArrayList<>();

        assertEquals(player.getHand(), test);

        BlackJackDeck deck = new BlackJackDeck();
        deck.makeDeck();

        player.hit(deck);
        assertEquals(player.getHand().size(), 1);
    }
    
    @Test
    public void testHit() {
        BlackJackDeck deck = new BlackJackDeck();
        deck.makeDeck();

        BlackJackPlayer player = new BlackJackPlayer();
        player.hit(deck);
        player.hit(deck);
        player.hit(deck);

        ArrayList<BlackJackCard> test = player.getHand();

        test.get(0).setValue(4);
        test.get(1).setValue(5);
        test.get(2).setValue(3);

        assertEquals(player.getHand().size(), 3);
        assertEquals(player.getHandSum(), 12);
    }
}
