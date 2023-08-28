package com.example.cs2340Project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WordleFunctionalityTests {
    private WordleGameFunctionality wordle;
    @Before
    public void initialize() {
        String[] words = {"hello", "world", "valid", "false", "coded", "scrum", "array", "apply", "paste", "words"};
        wordle = new WordleGameFunctionality(words);
    }

    @Test
    public void testSelectNewWord() {
        wordle.setNewWord("Hello");
        assertEquals("Hello", wordle.getSolution());
        wordle.selectNewWord();
        assertNotEquals("Hello", wordle.getSolution());
        String temp = wordle.getSolution();
        wordle.selectNewWord();
        assertNotEquals(temp, wordle.getSolution());
        temp = wordle.getSolution();
        wordle.selectNewWord();
        assertNotEquals(temp, wordle.getSolution());
        temp = wordle.getSolution();
        wordle.selectNewWord();
        assertNotEquals(temp, wordle.getSolution());
    }

    @Test
    public void testCheckGuessCorrectLetters() {
        String ans = "Hello";
        wordle.setNewWord(ans);
        int[] expected = {2,2,2,2,2};
        wordle.checkGuess(ans);
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "Calls";
        wordle.setNewWord(ans);
        wordle.checkGuess(ans);
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "Hello";
        wordle.setNewWord(ans);
        wordle.checkGuess(ans);
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "yeeHe";
        wordle.setNewWord(ans);
        wordle.checkGuess(ans);
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "tests";
        wordle.setNewWord(ans);
        wordle.checkGuess(ans);
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "tEstS";
        wordle.setNewWord(ans);
        wordle.checkGuess("TeSTs");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
    }

    @Test
    public void checkGuessValid() {
        String word = "hello";
        assertEquals(true, wordle.checkGuessValid(word) >= 0);
        word = "world";
        assertEquals(true, wordle.checkGuessValid(word) >= 0);
        word = "this";
        assertEquals(true, wordle.checkGuessValid(word) < 0);
        word = "mall";
        assertEquals(true, wordle.checkGuessValid(word) < 0);
        word = "valid";
        assertEquals(true, wordle.checkGuessValid(word) >= 0);
        word = "htrys";
        assertEquals(true, wordle.checkGuessValid(word) < 0);
    }

    @Test
    public void testHashcode() {
        char ascii = 'a';
        assertEquals(0, wordle.hashcode(ascii));
        ascii = 'b';
        assertEquals(1, wordle.hashcode(ascii));
        ascii = 'c';
        assertEquals(2, wordle.hashcode(ascii));
        ascii = 'd';
        assertEquals(3, wordle.hashcode(ascii));
        ascii = 'e';
        assertEquals(4, wordle.hashcode(ascii));
        assertEquals(wordle.hashcode('e'), wordle.hashcode(ascii));
    }

    @Test
    public void testClear() {
        wordle.push(0, 'a');
        wordle.push(1, 'l');
        assertTrue(wordle.get(0, 'a'));
        wordle.clear();
        assertFalse(wordle.get(0,'a'));
    }

    @Test
    public void testExistingLettersWrongIndex() {
        String ans = "candy";
        int[] expected = new int[]{1, 0, 0, 1, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("yoink");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "strap";
        expected = new int[]{1, 0, 0, 0, 1};
        wordle.setNewWord(ans);
        wordle.checkGuess("penis");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "hello";
        expected = new int[]{1, 0, 0, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("eager");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
    }

    @Test
    public void testGet() {
        wordle.push(0,'a');
        wordle.push(1,'b');
        wordle.push(2,'c');
        assertTrue(wordle.get(0,'a'));
        assertFalse(wordle.get(1,'g'));
    }

    @Test
    public void testNodeClass() {
        Node newNode = new Node(1, 'a');
        assertNull(newNode.next);
        Node node2 = new Node(1, 'b');
        newNode.next = node2;
        assertNull(newNode.next.next);
    }

    @Test
    public void testPush() {
        wordle.push(new Node(0,'a'));
        wordle.push(new Node(6,'b'));
        wordle.push(new Node(11,'c'));
        Node[] map1 = wordle.getWordHashmap();
        Node[] map2 = wordle.mapDeepCopy();
        assertNotEquals(map2, map1);
        assertEquals(true, map2[0].equals(map1[0].pos, map1[0].let));
    }

    @Test
    public void testRemove() {
        wordle.push(0, 'a');
        wordle.push(1, 'b');
        wordle.push(2, 'c');
        wordle.push(3, 'd');
        wordle.push(4, 'e');
        wordle.remove(0,'a');
        Node[] map = wordle.getWordHashmap();
        assertNull(map[0]);
        assertEquals(1,map[1].pos);
        assertEquals('b',map[1].let);
        wordle.remove(1,'b');
        map = wordle.getWordHashmap();
        assertNull(map[1]);
    }

    @Test
    public void testCheckGuess() {
        // Check if the method works if a guess is completely correct.
        String ans = "Hello";
        int[] expected = {2,2,2,2,2};

        // Test if part of word is correct and rest incorrect
        ans = "Hello";
        expected = new int[]{0, 2, 0, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("TeSTs");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "yoink";
        expected = new int[]{0, 2, 2, 2, 2};
        wordle.setNewWord(ans);
        wordle.checkGuess("zoink");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "china";
        expected = new int[]{2, 2, 2, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("child");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "guess";
        expected = new int[]{0, 0, 0, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("chill");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        ans = "pairs";
        expected = new int[]{2, 2, 0, 0, 2};
        wordle.setNewWord(ans);
        wordle.checkGuess("palms");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        // Test if letters are correct in wrong spot (with correct letters)
        ans = "trace";
        expected = new int[]{2, 1, 1, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("tarry");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        // A game of wordle I played and it's results:
        wordle.setNewWord("strap");
        expected = new int[]{2, 1, 0, 0, 0};
        wordle.checkGuess("spoil");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 0, 0, 2};
        wordle.checkGuess("stump");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("strap");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        // Game 2:
        ans = "billy";
        expected = new int[]{0, 0, 2, 2, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("Hello");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 0, 2, 2, 2};
        wordle.checkGuess("jelly");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 0, 2, 2, 2};
        wordle.checkGuess("bully");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 2, 2, 2, 2};
        wordle.checkGuess("silly");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("billy");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        // Game 3:
        ans = "sheer";
        expected = new int[]{0, 1, 0, 0, 1};
        wordle.setNewWord(ans);
        wordle.checkGuess("crane");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{1, 0, 0, 0, 1};
        wordle.checkGuess("range");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 0, 1, 2, 1};
        wordle.checkGuess("fires");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 0, 1, 2, 2};
        wordle.checkGuess("poser");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("sheer");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        //Game 4:
        ans = "scant";
        expected = new int[]{1, 0, 2, 2, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("crane");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("scant");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        //Game 5:
        ans = "wacky";
        expected = new int[]{1, 0, 1, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("crane");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 2, 0, 0, 2};
        wordle.checkGuess("parry");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{1, 2, 2, 0, 0};
        wordle.checkGuess("yacht");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("wacky");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 0, 0, 0, 0};
        wordle.checkGuess("hello");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        //Game 6:
        ans = "ranch";
        expected = new int[]{0, 0, 1, 0, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("plays");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 2, 1, 2, 2};
        wordle.checkGuess("march");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 0, 2, 2};
        wordle.checkGuess("ratch");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("ranch");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());

        //Game 7:
        ans = "ranch";
        expected = new int[]{0, 0, 0, 1, 0};
        wordle.setNewWord(ans);
        wordle.checkGuess("plead");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{0, 1, 1, 1, 0};
        wordle.checkGuess("shart");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{1, 0, 1, 0, 1};
        wordle.checkGuess("abhor");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
        expected = new int[]{2, 2, 2, 2, 2};
        wordle.checkGuess("ranch");
        assertArrayEquals(expected, wordle.getPlayerCorrectness());
    }
}
