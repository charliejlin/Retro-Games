package com.example.cs2340Project;

import android.widget.Toast;

public class TicTacToeFunctionality {
    private int[][] board;
    int playerPiece;
    int whosTurn;
    TicTacToeComputerMovement comp;

    /**
     * Makes a 3x3 tic tac toe board
     */
    public TicTacToeFunctionality() {
        board = new int[3][3];
        /**
         * The following sets the player piece based on the number of lives that the player has. We
         * can change this if we want to implement it in a different way.
         */
        Player player = Player.getInstance();
        if (player.getPlayerLives() % 2 == 0) {
            playerPiece = 1; // Set player piece to X's
        } else {
            playerPiece = 2; // Set player piece to O's
        }
        whosTurn = 1;
        comp = new TicTacToeComputerMovement(playerPiece);
    }

    /**
     * Method to set the player piece.
     *
     * @param piece What piece the player wants.
     */
    public void setPlayerPiece(int piece) {
        if (piece == 1) {
            playerPiece = piece;
            comp.setComputerPiece(2);
        } else if (piece == 2) {
            playerPiece = piece;
            comp.setComputerPiece(1);
        }
    }

    /**
     * Assume board is layed out like the following:
     * 1|2|3
     * 4|5|6
     * 7|8|9
     * @param game TicTacToe game to display the message.
     * @param where Where the user onces to place a piece.
     * @return Whether the piece can be added in the specified spot.
     */
    public boolean canPlacePiece(int where, TicTacToeGame game) {
        if (board[getRow(where)][getCol(where)] == 0) {
            return true;
        }
        if (game == null) {
            System.out.println("Cannot place piece here.");
        } else {
            Toast.makeText(game, "Cannot place piece here.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Helper method to get the row to place a piece.
     *
     * @param where Where the user wants to place a piece.
     * @return Which row to place the piece.
     */
    public int getRow(int where) {
        if (where >= 1 & where <= 3) {
            return 0;
        } else if (where >= 4 & where <= 6) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Helper method to get the col to place a piece.
     *
     * @param where Where the user wants to place a piece.
     * @return Which col to place the piece.
     */
    public int getCol(int where) {
        if (where % 3 == 1) {
            return 0;
        } else if (where % 3 == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Method to actually place the piece onto the board.
     * Return 1 (X win), 2 (O win), 0 (No winner), -1 (Invalid Move)
     *
     * @param where Where the user wants to place a piece.
     * @param game The instance of a game to work with.
     */
    public int placePiece(int where, TicTacToeGame game) {
         if (canPlacePiece(where, game)) {
            board[getRow(where)][getCol(where)] = playerPiece;
            updateTurn();
        }
        return checkForWinner();
    }

    /**
     * Gets the computer's move and places the piece.
     *
     * @param game The game activity.
     * @return if there is a winner or not.
     */
    public int[] placeCompPiece(TicTacToeGame game) {
        if (whosTurn != playerPiece) {
            int where = comp.getComputerMove(board, this);
            board[getRow(where)][getCol(where)] = comp.getComputerPiece();
            updateTurn();
            return new int[]{checkForWinner(), where};
        }
        return new int[]{-1, -1};
    }

    /**
     * Change the who's turn variable to the next turn.
     */
    public void updateTurn() {
        if (whosTurn == 1) {
            whosTurn = 2;
        } else {
            whosTurn = 1;
        }
    }

    /**
     * Helper method to check for a winner.
     *
     * @return If there is a winner or not (0 = none, 1 = X, 2 = O)
     */
    public int checkForWinner() {
        if (board[0][0] == board[0][1] && board[0][2] == board[0][0] && board[0][0] != 0) {
            return board[0][0]; //Check first row

        } else if (board[0][0] == board[1][0] && board[0][0] == board[2][0] && board[0][0] != 0) {
            return board[0][0]; //Check first col

        } else if (board[0][1] == board[1][1] && board[0][1] == board[2][1] && board[0][1] != 0) {
            return board[0][1]; //Check sec col

        } else if (board[0][2] == board[1][2] && board[0][2] == board[2][2] && board[0][2] != 0) {
            return board[0][2]; //Check third col

        } else if (board[1][0] == board[1][1] && board[1][2] == board[1][0] && board[1][1] != 0) {
            return board[1][0]; //Check sec row

        } else if (board[2][0] == board[2][1] && board[2][2] == board[2][0] && board[2][2] != 0) {
            return board[2][0]; //Check third row

        } else if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return board[0][0]; //Check main diagonal
        } else if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] != 0) {
            return board[1][1]; // Check minor diagonal
        }

        return 0;
    }

    /**
     * Getter to get a piece on the board.
     *
     * @param where Where to pull the piece from.
     * @return The piece at that spot.
     */
    public int getPiece(int where) {
        return board[getRow(where)][getCol(where)];
    }

    /**
     * Returns the current board.
     *
     * @return Board int[][].
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Getter to obtain the player piece.
     *
     * @return The player's piece represented as 1 (X) or 2 (O)
     */
    public int getPlayerPiece() {
        return playerPiece;
    }

    /**
     * Getter for whosTurn.
     *
     * @return whosTurn.
     */
    public int getWhosTurn() {
        return whosTurn;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        whosTurn = 1;
    }
}

