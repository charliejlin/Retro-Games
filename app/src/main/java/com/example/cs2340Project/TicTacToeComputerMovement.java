package com.example.cs2340Project;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToeComputerMovement {

    int computerPiece;

    /**
     * Constructor for computer movement.
     *
     * @param playerPiece Make computer opposite player piece.
     */
    public TicTacToeComputerMovement(int playerPiece) {
        if (playerPiece == 1) {
            computerPiece = 2;
        } else {
            computerPiece = 1;
        }
    }

    /**
     * Checks if the other player has 2 pieces "in a row"
     *
     * @param board Current board being played with.
     * @return 0 If there is no potential win, where to place if there is.
     */
    public int checkAlmostWin(int[][] board, int checkFor) {
        // Checks rows for almost wins
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == checkFor && board[i][1] == checkFor && board[i][2] == 0) {
                return 3 * i + 3;
            }
            if (board[i][0] == checkFor && board[i][2] == checkFor && board[i][1] == 0) {
                return 3 * i + 2;
            }
            if (board[i][2] == checkFor && board[i][1] == checkFor && board[i][0] == 0) {
                return 3 * i + 1;
            }
        }
        // Checks cols for almost wins
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == checkFor && board[1][i] == checkFor && board[2][i] == 0) {
                return 7 + i;
            }
            if (board[0][i] == checkFor && board[2][i] == checkFor && board[1][i] == 0) {
                return 4 + i;
            }
            if (board[2][i] == checkFor && board[1][i] == checkFor && board[0][i] == 0) {
                return 1 + i;
            }
        }
        //check main diag
        if (board[0][0] == checkFor && board[1][1] == checkFor && board[2][2] == 0) {
            return 9;
        }
        if (board[0][0] == checkFor && board[1][1] == 0 && board[2][2] == checkFor) {
            return 5;
        }
        if (board[0][0] == 0 && board[1][1] == checkFor && board[2][2] == checkFor) {
            return 1;
        }
        //check minor diag
        if (board[0][2] == checkFor && board[1][1] == checkFor && board[2][0] == 0) {
            return 7;
        }
        if (board[0][2] == checkFor && board[1][1] == 0 && board[2][0] == checkFor) {
            return 5;
        }
        if (board[0][2] == 0 && board[1][1] == checkFor && board[2][0] == checkFor) {
            return 3;
        }
        return 0;
    }

    /**
     * Method to led the computer chose a move.
     * - Prioritizes the computer winning/stopping the player from winning.
     *
     * @param board Current state of the board.
     * @param game To use for getPossibleMoves
     * @return What the computer's move is.
     */
    public int getComputerMove(int[][] board, TicTacToeFunctionality game) {
        int where = checkAlmostWin(board, computerPiece);
        if (where != 0) {
            return where;
        }
        where = checkAlmostWin(board, game.getPlayerPiece());
        if (where != 0) {
            return where;
        }
        ArrayList<Integer> posMoves = getPossibleMoves(board, game);
        Random rand = new Random();
        return posMoves.get(rand.nextInt(posMoves.size()));
    }

    /**
     * Check for possible moves that the computer can randomly chose from.
     *
     * @param board Current state of the board.
     * @param game Game that is working with (used for canPlacePiece function).
     * @return ArrayList of possible moves.
     */
    public ArrayList<Integer> getPossibleMoves(int[][] board, TicTacToeFunctionality game) {
        ArrayList<Integer> out = new ArrayList<Integer>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (game.canPlacePiece(3 * i + 1 + j, null)) {
                    out.add(3 * i + 1 + j);
                }
            }

        }
        return out;
    }

    /**
     * Get computerPiece
     *
     * @return computerPiece
     */
    public int getComputerPiece() {
        return computerPiece;
    }

    /**
     * Setter for the computer piece.
     *
     * @param computerPiece What to update the piece to.
     */
    public void setComputerPiece(int computerPiece) {
        this.computerPiece = computerPiece;
    }
}

