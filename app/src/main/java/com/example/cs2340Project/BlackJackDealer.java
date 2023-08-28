package com.example.cs2340Project;

public class BlackJackDealer extends BlackJackPlayer {

    /**
     * Hits the dealer's hand based on casino rules.
     *
     * @param deck Deck to pull cards from.
     */
    public void dealerHit(BlackJackDeck deck) {
        while (this.getHandSum() < 17) {
            this.hit(deck);
        }
    }

    /**
     * Player to check if a given player beat the dealer.
     * 2 - Player wins
     * 1 - Tie
     * 0 - Player lost
     *
     * @param player Player to check if they won.
     * @return Returns state of player win/loss
     */
    public int playerWin(BlackJackPlayer player) {
        if (this.getHandSum() > 21) {
            if (player.getHandSum() < 22) {
                return 2;
            } else {
                return 0;
            }
        } else {
            if (player.getHandSum() == this.getHandSum()) {
                return 1;
            } else if (player.getHandSum() < this.getHandSum()) {
                return 0;
            } else {
                return 2;
            }
        }
    }

    /**
     * Get the card shown (index 0).
     *
     * @return The index 0 card (card to be shown).
     */
    public BlackJackCard getShownCard() {
        return this.getHand().get(0);
    }

    /**
     * Prints the dealers hand.
     */
    //@Override
    public void printHand() {
        System.out.printf("Dealer hand:\n");
        for (BlackJackCard card: this.getHand()) {
            System.out.printf("%s\n",card);
        }
    }

    /**
     * Shows player the single shown card.
     */
    public void printShownCard() {
        System.out.printf("Dealer card:\n%s\n",this.getShownCard());
    }
}
