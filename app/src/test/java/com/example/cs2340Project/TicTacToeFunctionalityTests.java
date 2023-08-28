package com.example.cs2340Project;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeFunctionalityTests {

    TicTacToeFunctionality game;
    TicTacToeComputerMovement comp;
    @Before
    public void initialize() {
        game = new TicTacToeFunctionality();
        comp = new TicTacToeComputerMovement(game.getPlayerPiece());
    }

    // 1
    @Test
    public void testSetPlayerPiece() {
        game.setPlayerPiece(1);
        assertEquals(game.getPlayerPiece(), 1);
        game.setPlayerPiece(2);
        assertEquals(game.getPlayerPiece(), 2);
    }

    //2
    @Test
    public void testGetBoard() {
        int[][] expected = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(expected, game.getBoard());

        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(2, null);
        game.updateTurn();
        game.placePiece(3, null);
        game.updateTurn();
        expected = new int[][]{{1,1,1},{0,0,0},{0,0,0}};
        assertArrayEquals(expected, game.getBoard());

        game = new TicTacToeFunctionality();
        game.setPlayerPiece(2);
        game.updateTurn();
        game.placePiece(4, null);
        game.updateTurn();
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(6, null);
        game.updateTurn();
        expected = new int[][]{{0,0,0},{2,2,2},{0,0,0}};
        assertArrayEquals(expected, game.getBoard());

        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(9, null);
        game.updateTurn();
        expected = new int[][]{{1,0,0},{0,1,0},{0,0,1}};
        assertArrayEquals(expected, game.getBoard());
    }

    //3
    @Test
    public void testCanPlacePiece() {
        assertEquals(true, game.canPlacePiece(1,null));
        assertEquals(true, game.canPlacePiece(2,null));
        assertEquals(true, game.canPlacePiece(3,null));
        assertEquals(true, game.canPlacePiece(4,null));
        assertEquals(true, game.canPlacePiece(5,null));
        assertEquals(true, game.canPlacePiece(6,null));
        assertEquals(true, game.canPlacePiece(7,null));
        assertEquals(true, game.canPlacePiece(8,null));
        assertEquals(true, game.canPlacePiece(9,null));
    }

    //4
    @Test
    public void testPlacePiece() {
        game.setPlayerPiece(1);
        assertEquals(1, game.getWhosTurn());
        game.placePiece(1, null);
        assertEquals(1, game.getBoard()[0][0]);
        assertEquals(2, game.getWhosTurn());
    }

    //5
    @Test
    public void testUpdateTurn() {
        assertEquals(1, game.getWhosTurn());
        game.updateTurn();
        assertEquals(2, game.getWhosTurn());
        game.updateTurn();
        assertEquals(1, game.getWhosTurn());
    }

    //6
    @Test
    public void checkTestForWinner() {
        game.setPlayerPiece(1);
        assertEquals(0, game.checkForWinner());
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(2, null);
        game.updateTurn();
        game.placePiece(3, null);
        game.updateTurn();
        assertEquals(1, game.checkForWinner());
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(2);
        assertEquals(0, game.checkForWinner());
        game.updateTurn();
        game.placePiece(4, null);
        game.updateTurn();
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(6, null);
        game.updateTurn();
        assertEquals(2, game.checkForWinner());
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        assertEquals(0, game.checkForWinner());
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(9, null);
        game.updateTurn();
        assertEquals(1, game.checkForWinner());
    }

    //7
    @Test
    public void testGetPiece() {
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(2, null);
        game.updateTurn();
        game.placePiece(3, null);
        assertEquals(1, game.getPiece(1));
        assertEquals(1, game.getPiece(2));
        assertEquals(1, game.getPiece(3));
        assertEquals(0, game.getPiece(4));
        assertEquals(0, game.getPiece(5));
        assertEquals(0, game.getPiece(6));
        assertEquals(0, game.getPiece(7));
        assertEquals(0, game.getPiece(8));
        assertEquals(0, game.getPiece(9));
    }

    //8
    @Test
    public void testCheckAlmostWin() {
        // x - x
        // - - -
        // - - -
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(3, null);
        assertEquals(2, comp.checkAlmostWin(game.getBoard(), 1));

        // x x -
        // - - -
        // - - -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(2, null);
        assertEquals(3, comp.checkAlmostWin(game.getBoard(), 1));

        // - - -
        // - - -
        // - x x
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(8, null);
        game.updateTurn();
        game.placePiece(9, null);
        assertEquals(7, comp.checkAlmostWin(game.getBoard(), 1));

        // x - -
        // - - -
        // x - -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(7, null);
        assertEquals(4, comp.checkAlmostWin(game.getBoard(), 1));

        // - - x
        // - - -
        // - - x
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(3, null);
        game.updateTurn();
        game.placePiece(9, null);
        assertEquals(6, comp.checkAlmostWin(game.getBoard(), 1));

        // - - x
        // - - -
        // x - -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(3, null);
        game.updateTurn();
        game.placePiece(7, null);
        assertEquals(5, comp.checkAlmostWin(game.getBoard(), 1));

        // - - x
        // - x -
        // - - -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(3, null);
        game.updateTurn();
        game.placePiece(5, null);
        assertEquals(7, comp.checkAlmostWin(game.getBoard(), 1));

        // x - -
        // - - -
        // - - x
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(9, null);
        assertEquals(5, comp.checkAlmostWin(game.getBoard(), 1));

        // - - -
        // - x -
        // - - x
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(9, null);
        assertEquals(1, comp.checkAlmostWin(game.getBoard(), 1));

        // x - -
        // - x -
        // - - -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(5, null);
        assertEquals(9, comp.checkAlmostWin(game.getBoard(), 1));

        // x - -
        // - - -
        // - x -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(8, null);
        assertEquals(0, comp.checkAlmostWin(game.getBoard(), 1));
    }

    //9
    @Test
    public void testGetPossibleMoves() {
        // x - -
        // - - -
        // - x -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(8, null);
        assertEquals(0, comp.checkAlmostWin(game.getBoard(), 1));
        int[] expected = new int[]{2,3,4,5,6,7,9};
        assertArrayEquals(expected, makeArrayFromList());

        // - - x
        // - - -
        // - x -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(3, null);
        game.updateTurn();
        game.placePiece(8, null);
        assertEquals(0, comp.checkAlmostWin(game.getBoard(), 1));
        expected = new int[]{1,2,4,5,6,7,9};
        assertArrayEquals(expected, makeArrayFromList());

        // x x x
        // x x x
        // x x -
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.updateTurn();
        game.placePiece(2, null);
        game.updateTurn();
        game.placePiece(3, null);
        game.updateTurn();
        game.placePiece(4, null);
        game.updateTurn();
        game.placePiece(5, null);
        game.updateTurn();
        game.placePiece(6, null);
        game.updateTurn();
        game.placePiece(7, null);
        game.updateTurn();
        game.placePiece(8, null);
        expected = new int[]{9};
        assertArrayEquals(expected, makeArrayFromList());
    }

    //10
    @Test
    public void testGetComputerMove() {
        Random rand = new Random();

        game.setPlayerPiece(1);
        game.placePiece(1, null);
        game.placeCompPiece(null);
        int nextMove = comp.getPossibleMoves(game.getBoard(), game).get(rand.nextInt(comp.getPossibleMoves(game.getBoard(), game).size()));
        game.placePiece(nextMove, null);
        game.placeCompPiece(null);
        assertEquals(4, checkNumPiecesOnBoard(game.getBoard()));

        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(5, null);
        game.placeCompPiece(null);
        nextMove = comp.getPossibleMoves(game.getBoard(), game).get(rand.nextInt(comp.getPossibleMoves(game.getBoard(), game).size()));
        game.placePiece(nextMove, null);
        game.placeCompPiece(null);
        assertEquals(4, checkNumPiecesOnBoard(game.getBoard()));

        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        game.placePiece(5, null);
        game.placeCompPiece(null);
        nextMove = comp.getPossibleMoves(game.getBoard(), game).get(rand.nextInt(comp.getPossibleMoves(game.getBoard(), game).size()));
        game.placePiece(nextMove, null);
        game.placeCompPiece(null);
        nextMove = comp.getPossibleMoves(game.getBoard(), game).get(rand.nextInt(comp.getPossibleMoves(game.getBoard(), game).size()));
        game.placePiece(nextMove, null);
        game.placeCompPiece(null);
        assertEquals(6, checkNumPiecesOnBoard(game.getBoard()));
    }

    @Test
    public void testResetBoard() {
        game = new TicTacToeFunctionality();
        game.placePiece(1, null);
        game.placePiece(7, null);
        game.placePiece(5, null);
        game.placePiece(8, null);
        game.resetBoard();
        assertEquals(0, game.getPiece(1));
        assertEquals(0, game.getPiece(2));
        assertEquals(0, game.getPiece(3));
        assertEquals(0, game.getPiece(4));
        assertEquals(0, game.getPiece(5));
        assertEquals(0, game.getPiece(6));
        assertEquals(0, game.getPiece(7));
        assertEquals(0, game.getPiece(8));
        assertEquals(0, game.getPiece(9));
    }


    /**
     *  Return Arraylist as an array.
     *
     *  @return Arraylist as an array.
     *  - I know there is a toArray() function... but just in case ;p
     */
    private int[] makeArrayFromList() {
        ArrayList<Integer> out = comp.getPossibleMoves(game.getBoard(), game);
        int[] actual = new int[out.size()];
        for (int i = 0; i < actual.length; ++i) {
            actual[i] = out.get(i);
        }
        return actual;
    }

    /**
     * Get the number of pieces on the board for testing.
     *
     * @return Number of pieces on the board.
     */
    private int checkNumPiecesOnBoard(int[][] board) {
        int counter = 0;
        for (int i = 0;i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] != 0) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
