package com.example.cs2340Project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TicTacToeGame extends AppCompatActivity implements LivesSelectable {
    private Button restartButton;
    private Button homeButton;
    private Player player1;
    private int winner;
    private int movesLeft;
    private TicTacToeFunctionality game;
    private TicTacToeComputerMovement computerMovement;
    private ArrayList<Button> gameButtons;
    private boolean playerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_game);

        movesLeft = 9;
        restartButton = findViewById(R.id.t_restart_button);
        player1 = Player.getInstance();
        game = new TicTacToeFunctionality();
        game.setPlayerPiece(1);
        computerMovement = new TicTacToeComputerMovement(game.getPlayerPiece());
        gameButtons = new ArrayList<>();
        playerTurn = true;
        winner = 0;

        initializeButtons();
        selectLives();

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.setScore(0);
                player1.setPlayerLives(3);
                startNewGame();
            }
        });

        homeButton = findViewById(R.id.t_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicTacToeGame.this, MainActivity.class);
                player1.setPlayerLives(3);
                startActivity(intent);
            }
        });
    }

    private void initializeButtons() {
        for (int i = 0; i < 9; i++) {
            int resId = getResources().getIdentifier("grid" + (i / 3) + (i % 3), "id", getPackageName());
            Button button = findViewById(resId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    afterPlayerMove(button);
                }
            });
            gameButtons.add(button);

            button.setText("Click to select");
        }
    }

    private void makePlayerMove(int index, Button button) {
        int winner = game.placePiece(index, this);
        if (winner != -1) {
            button.setEnabled(false);
            button.setText("X");
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))); // Change the clicked button's background color to yellow
            movesLeft--;
            playerTurn = false;
            if (movesLeft == 0 || winner > 0) {
                handleGameEnd(winner);
            } else {
                makeComputerMove();
            }
        }
    }

    private void afterPlayerMove(Button button) {
        if (playerTurn) {
            int buttonIndex = gameButtons.indexOf(button);
            makePlayerMove(buttonIndex + 1, button);
        }
    }

    private void makeComputerMove() {
        if (!playerTurn) {
            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int[] compMove = game.placeCompPiece(TicTacToeGame.this);
                    Button button = gameButtons.get(compMove[1]);
                    button.setText("O");
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(TicTacToeGame.this, R.color.red)));

                    afterComputerMove(compMove[1], R.color.red);

                    handleGameEnd(compMove[0]);
                    playerTurn = true;
                }
            }, 200); // Delay in milliseconds before the computer moves
             */
            int[] compMove = game.placeCompPiece(TicTacToeGame.this);
            Button button = gameButtons.get(compMove[1] - 1);
            button.setEnabled(false);
            button.setText("O");
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(TicTacToeGame.this, R.color.red)));
            movesLeft--;
            afterComputerMove(compMove[1] - 1, R.color.red);
            if (compMove[0] != 0) {
                handleGameEnd(compMove[0]);
            }
            playerTurn = true;
        }
    }

    private void afterComputerMove(int index, int colorResource) {
        Button button = gameButtons.get(index);
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colorResource)));
    }


    private void updateBoardUI() {
        int[][] board = game.getBoard();
        for (int i = 0; i < 9; i++) {
            Button button = gameButtons.get(i);
            int row = i / 3;
            int col = i % 3;
            int piece = board[row][col];
            if (piece == 1) {
                button.setText("X");
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)));
            } else if (piece == 2) {
                button.setText("O");
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
            } else {
                button.setText("");
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
            }
        }
    }

    private void handleGameEnd(int winner) {
        String message;

        switch (winner) {
            case 1:
                message = "Player wins!";
                player1.setScore(player1.getScore() + 1);
                break;
            case 2:
                message = "Computer wins!";
                player1.setPlayerLives(player1.getPlayerLives() - 1);
                selectLives();
                break;
            default:
                message = "It's a draw!";
                break;
        }

        showMessageDialog(message);
        startNewGame();
    }

    private void showMessageDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void startNewGame() {
        Intent intent = new Intent(TicTacToeGame.this, TicTacToeGame.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void selectLives() {
        if (player1.getPlayerLives() == 0) {
            Intent intent = new Intent(TicTacToeGame.this, GameOverScreen.class);
            startActivity(intent);
            finish();
        }
        TextView name = findViewById(R.id.playerDataName);
        name.setText(player1.getPlayerName());
        name.setTextColor(-1);
        //update the sprite image
        ImageView sprite = findViewById(R.id.sprite);
        player1.setSpriteImage(sprite);
        ImageView i1 = findViewById(R.id.life1);
        ImageView i2 = findViewById(R.id.life2);
        ImageView i3 = findViewById(R.id.life3);
        player1.setLives(i1, i2, i3);
    }
}
