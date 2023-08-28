package com.example.cs2340Project;
import android.widget.TextView;

import java.util.Random;

/**
 * Class to help manage the functionality of the wordle game.
 *
 * @version 1.0
 */
public class WordleGameFunctionality {

    private static String[] wordList;

    /** LinkedList-backed/Stack-backed hashmap for finding positions of words. */
    private Node[] wordHashmap;

    /** Int array to represent the player's level of correctness.
     * 0 - Represents completely incorrect
     * 1 - Represents letter is in word, but in incorrect position.
     * 2 - Represents letter is in word and in correct position.
     */
    private int[] playerCorrectNess;

    /** String of the correct word. */
    private static String solution;

    private boolean gameWon = false;

    /**
     * Constructor for the class to operate the correct word/
     * player correctness on guesses.
     */
    public WordleGameFunctionality(String[] words) {
        wordList = words;
        wordHashmap = new Node[5];
        playerCorrectNess = new int[5];
    }

    /**
     * Method to generate a hashcode/get the ind of a letter in the hashmap.
     *
     * @param currLet Letter to create hashcode for.
     * @return An integer of the hashcode.
     */
    public int hashcode(char currLet) {
        currLet = Character.toLowerCase(currLet);
        return ((int) currLet - (int) 'a') % wordHashmap.length;
    }

    /**
     * Method to push the letters of a word and position of letter to the hashmap.
     *
     * @param let Letter getting added.
     * @param pos Indice/position of the letter in the original String.
     */
    public void push(int pos, char let) {
        int ind = this.hashcode(let);
        if (wordHashmap[ind] == null) {
            wordHashmap[ind] = new Node(pos, let);
        } else {
            Node last = wordHashmap[ind];
            while (last.next != null) {
                last = last.next;
            }
            last.next = new Node(pos, let);
        }
    }

    /**
     * Method to push the letters of a word and position of letter to the hashmap.
     *
     * @param node Node to add to the hashmap.
     */
    public void push(Node node) {
        this.push(node.pos, node.let);
    }

    /**
     * Method to get the Node containing a letter.
     *
     * @param let Letter key being looked for
     * @return Node value associated with the key
     *      (null if it is not a part of the solution).
     */
    public Node get(char let) {
        int pos = this.hashcode(let);
        Node out = wordHashmap[pos];
        if (out == null) {
            return out;
        } else {
            while (out != null) {
                if (out.let == let) {
                    return out;
                }
                out = out.next;
            }
            return out;
        }
    }

    /**
     * Getter to check if the indice, letter pair exists in the map.
     *
     * @param indInWords Indice of the letter in the player's guess
     * @param let Letter getting checked.
     * @return True or false on if correct letter in correct position.
     */
    public boolean get(int indInWords, char let) {
        int pos = this.hashcode(let);
        Node out = wordHashmap[pos];
        while (out != null) {
            if (out.equals(indInWords, let)) {
                return true;
            }
            out = out.next;
        }
        return false;

    }

    /**
     * Method to remove a letter given its position and return whether
     * it was found or not.
     *
     * @param pos Position of the letter in the word.
     * @param let Letter being looked for.
     * @return boolean representing if Combination exists/was removed.
     */
    public boolean remove(int pos, char let) {
        int ind = this.hashcode(let);
        Node follower = wordHashmap[ind];
        if (follower == null) {
            return false;
        } else {
            if (follower.next == null) {
                if (follower.equals(pos, let)) {
                    wordHashmap[ind] = null;
                    return true;
                } else {
                    return false;
                }
            } else if (follower.equals(pos,let)) {
                wordHashmap[ind] = follower.next;
                return true;
            } else {
                Node leader = follower.next;
                while (leader != null) {
                    if (leader.equals(pos,let)) {
                        follower.next = leader.next;
                        return true;
                    } else {
                        leader = leader.next;
                        follower = follower.next;
                    }
                }
                return false;
            }
        }
    }

    /**
     * Method to clear the current word.
     */
    public void clear() {
        wordHashmap = new Node[5];
        playerCorrectNess = new int[5];
    }

    /**
     * Method to randomly select a new word from previous
     * wordle words.
     */
    public void selectNewWord() {
        Random rand = new Random();
        String selection = solution;
        if (selection == null) {
            int whichWord = rand.nextInt(wordList.length);
            selection = wordList[whichWord];
        } else {
            while (selection.equals(solution)) {
                int whichWord = rand.nextInt(wordList.length);
                selection = wordList[whichWord];
            }
        }
        this.setNewWord(selection);
    }

    /**
     * Method to set a given word as the class's correct word.
     *
     * @param word Word to be set to the correct word.
     * @throws java.lang.IllegalArgumentException When word is not 5 characters.
     */
    public void setNewWord(String word) {
        if (word.length() != 5) {
            throw new IllegalArgumentException("Word given does not have length 5");
        }
        this.clear();
        solution = word;
        for (int i = 0; i < word.length(); ++i) {
            push(i, word.charAt(i));
        }
    }

    /**
     * Returns the current solution (for testing purposes).
     *
     * @return Returns the solution (String).
     */
    public static String getSolution() {
        return solution;
    }

    /**
     * Returns the correctness of the player's last guess.
     *
     * @return int Array representing the correctness of a player's guess.
     */
    public int[] getPlayerCorrectness() {
        return playerCorrectNess;
    }

    /**
     * Method to check and update player's correctness.
     * Logic:
     *  We need to check if a letter is in the correct word at the correct position.
     *  The first pass:
     *      Check if the letter is in the correct spot/in the correct word.
     *      This implements checkGuessCorrLets.
     *      However, we don't want to "permanently" remove something in the event that a
     *      user use a completely different word (regardless of correct letters already being found).
     *      As such, we need to recheck the entire word each time.
     *      To do this, make a copy of the word hash that we can save for the end of the function.
     *      In the meantime, freely remove data through the first pass. This will leave only:
     *          1.) Completely incorrect Letters
     *          2.) Letters that are correct in the wrong spot.
     *  Second pass:
     *      Check for letters that are in the word, but in the wrong.
     *      Also, needs to account for duplicates of letters.
     *
     * @param guess The player's guess
     */
    public void checkGuess(String guess) {
        Node[] temp = this.mapDeepCopy();
        playerCorrectNess = new int[5];
        // First pass: check for correct letters in the whole word.
        boolean[] correctLetters = checkGuessCorrLets(guess);
        for (int i = 0; i < 5; ++i) {
            if (correctLetters[i]) {
                playerCorrectNess[i] = 2;
            }
        }
        boolean[] almostLetters = checkGuessCorrLetsWrongSpot(guess);
        for (int i = 0; i < 5; i++) {
            if (almostLetters[i]) {
                playerCorrectNess[i] = 1;
            }
        }
        wordHashmap = temp;
    }

    /**
     * Helper method to simply go through the word and see which letters are
     * in the correct positions.
     *
     * @param guess
     * @return
     */
    private boolean[] checkGuessCorrLets(String guess) {
        boolean[] match = new boolean[5];
        for (int c = 0; c < guess.length(); c++) {
            match[c] = this.remove(c, guess.charAt(c));
            // set UI with index c to green
        }
        return match;
    }

    private boolean[] checkGuessCorrLetsWrongSpot(String guess) {
        boolean[] match = new boolean[5];
        for (int c = 0; c < guess.length(); c++) {
            Node nodeForLet = this.get(guess.charAt(c));
            if (playerCorrectNess[c] != 2 && nodeForLet != null) {
                match[c] = this.remove(nodeForLet.pos, nodeForLet.let);
            }
        }
        return match;
    }

    public int checkGuessValid(String guess){
        return checkGuessValid(wordList, guess);
    }

    /**
     * Binary search method to find if guess is valid.
     *
     * @param gL
     * @param guess
     * @return int rep of position in list
     */
    private int checkGuessValid(String[] gL, String guess) {
        int l = 0, r = gL.length - 1;
        for(int i =0; i< gL.length; i++){
            if(guess.compareTo(gL[i]) == 0){
                return i;
            }
        }
        return -1;
        // Loop to implement Binary Search
//        while (l <= r) {
//
//            // Calculate mid
//            int m = l + (r - l) / 2;
//
//            int res = guess.compareTo(gL[m]);
//
//            // Check if x is present at mid
//            if (res == 0)
//                return m;
//
//            // If x greater, ignore left half
//            if (res > 0)
//                l = m + 1;
//
//                // If x is smaller, ignore right half
//            else
//                r = m - 1;
//        }
//
//        return -1;
    }

    public Node[] mapDeepCopy() {
        Node[] map = new Node[5];
        Node curNode;
        Node newNodes = null;
        boolean first;
        for (int i = 0; i < 5; ++i) {
            curNode = wordHashmap[i];
            first = true;
            while (curNode != null) {
                if (first) {
                    newNodes = new Node(curNode.pos, curNode.let);
                    map[i] = newNodes;
                    first = false;
                } else {
                    newNodes.next = new Node(curNode.pos, curNode.let);
                    newNodes = newNodes.next;
                }
                curNode = curNode.next;
            }
        }
        return map;
    }

    /**
     * Getter method to update the UI.
     *
     * @return int[] representing the correctness of the
     * player's guess.
     */
    public int[] getPlayerCorrectNess() {
        return playerCorrectNess;
    }

    /**
     * Getter method to test the class.
     *
     * @return The word hashmap.
     */
    public Node[] getWordHashmap() {
        return wordHashmap;
    }


}
