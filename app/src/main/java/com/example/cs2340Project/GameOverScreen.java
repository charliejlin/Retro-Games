package com.example.cs2340Project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameOverScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen);

        Button home = findViewById(R.id.go_home_button);
        Button restart = findViewById(R.id.go_restart_button);

        Player player = Player.getInstance();

        String game = getIntent().getStringExtra("Game");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverScreen.this, MainActivity.class);
                player.setPlayerLives(3);
                startActivity(intent);
                finish();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverScreen.this, TicTacToeInitialScreen.class);
                if (game.equals("BJ")) {
                    intent = new Intent(GameOverScreen.this, BlackJackInitialScreen.class);
                } else if (game.equals("WD")) {
                    intent = new Intent(GameOverScreen.this, WordleInitialScreen.class);
                } else {
                    intent = new Intent(GameOverScreen.this, TicTacToeInitialScreen.class);
                }
                player.setPlayerLives(3);
                startActivity(intent);
                finish();
            }
        });

        TextView latestScore = findViewById(R.id.go_game_score);
        latestScore.setText("LATEST SCORE: " + player.getScore());
        if (player.getScore() > player.getHighScore()) {
            player.setHighScore(player.getScore());
        }
        TextView highScore = findViewById(R.id.go_game_high_score);
        highScore.setText("HIGH SCORE: " + player.getHighScore());
        player.setScore(0);

    }
}
